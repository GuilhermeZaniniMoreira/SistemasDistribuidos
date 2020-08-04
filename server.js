const express = require('express');
const path = require('path');

const app = express();
const server = require('http').createServer(app);
const io = require('socket.io')(server);

app.use(express.static(path.join(__dirname, 'public')));
app.set('views', path.join(__dirname, 'public'));
app.engine('html', require('ejs').renderFile);
app.set('view engine', 'html');

app.get('/', (req, res) => {
    res.render('index');
});

socketIds = [];
clientsClock = [];
clients = 0;

io.on('connection', socket => {
    if (!socketIds.includes(socket.id) && clients <= 4) {
        socketIds.push(socket.id);
        clients++;
        io.emit('broadcast', socketIds);
        if (socketIds.length == 4) {
            io.emit('four-clients');
        }
    }

    socket.on('clock', client => {
        clientsClock.push(client);
        if (clientsClock.length == 4) {
            results = berkeley(clientsClock);
            for (result of results) {
                var data = {
                    'signal': result.update < 0 ? 'atrasado' : 'adiantado',
                    'update': result.update < 0 ? millisToMinutesAndSeconds(result.update * -1) : millisToMinutesAndSeconds(result.update),
                }
                io.to(result.socketId).emit('result', data);
            }
            clientsClock = []; // depois dos 4 inicia novamente
        }
    });

    function berkeley(clientsClock) {

        /* 
        * tolerância de 5 minutos, em milissegundos.
        * se um relógio estiver com 5 minutos (300000)
        * a mais ou a menos que o relógio do deamon 
        * então o relógio do cliente não será utilizado
        * no cálculo da média
        */

        let tolerant = 300000;
        let serverClock = Date.now();
        let availableClients = [];
        let notAvaiableClients = [];

        for (client of clientsClock) {
            if (client.date <= (serverClock + tolerant) &&
                client.date >= (serverClock - tolerant)) {
                availableClients.push(client);
            } else {
                notAvaiableClients.push(client);
            }
        }

        const clientsSum = availableClients.map(item => item.date).reduce((prev, next) => prev + next);
        const sum = clientsSum + serverClock;
        const avg = (sum / (availableClients.length + 1)) || 0; // adicionando 1 -> clock do servidor

        var updatedClock = [];
        
        for (client of clientsClock) {
            var update = (avg - client.date);
            updatedClock.push({
                'socketId': client.socketId,
                'update': update
            });
        }

        for (client of notAvaiableClients) {
            var update = (avg - client.date);
            updatedClock.push({
                'socketId': client.socketId,
                'update': update
            });
        }
        return updatedClock;
    }
});

function millisToMinutesAndSeconds(millis) {
    var minutes = Math.floor(millis / 60000);
    var seconds = ((millis % 60000) / 1000).toFixed(0);
    return minutes + ":" + (seconds < 10 ? '0' : '') + seconds;
}

server.listen(process.env.PORT || 5000);
