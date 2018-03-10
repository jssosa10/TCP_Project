import socket
import multiprocessing
import os
def procesar(data,conn):
        data = data.rstrip()
        print(str(data))
        if 'getdir' in data:
                data = data.split(':')[1]
                lista = os.listdir(data)
                lista2 = [str(x)+':::'+str(os.path.getsize(data+'/'+str(x))) for x in lista]
                myList = ';'.join(map(str, lista2))
                return myList+'\n'
        elif 'getfile' in data:
                data = data.split(':')[1]
                f = open(str(data),'rb')
                l = f.read(1024)
                while l:
                        conn.send(l)
                        l = f.read(1024)
                f.close()
                return 'endfile\n'
def handle(connection, address):
    import logging
    logging.basicConfig(level=logging.DEBUG)
    logger = logging.getLogger("process-%r" % (address,))
    try:
        logger.debug("Connected %r at %r", connection, address)
        data = connection.recv(1024)
        can = False
        if 'Holiwi' in data:
                can = True
                connection.sendall(data)
        while can:
            data = connection.recv(1024)
            if data == "":
                logger.debug("Socket closed remotely")
                break
            logger.debug("Received data %r", data)
            connection.sendall(procesar(data,connection))
            logger.debug("Sent data")
    except:
        logger.exception("Problem handling request")
    finally:
        logger.debug("Closing socket")
        connection.close()

class Server(object):
        def __init__(self, hostname, port):
                import logging
                self.logger = logging.getLogger("server")
                self.hostname = hostname
                self.port = port

        def start(self):
                self.logger.debug("listening")
                self.socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
                self.socket.bind((self.hostname, self.port))
                self.socket.listen(1)

                while True:
                        conn, address = self.socket.accept()
                        self.logger.debug("Got connection")
                        process = multiprocessing.Process(target=handle, args=(conn, address))
                        process.daemon = True
                        process.start()
                        self.logger.debug("Started process %r", process)
if __name__ == "__main__":
    import logging
    logging.basicConfig(level=logging.DEBUG)
    server = Server('', 9000)
    try:
        logging.info("Listening")
        server.start()
    except:
        logging.exception("Unexpected exception")
    finally:
        logging.info("Shutting down")
        for process in multiprocessing.active_children():
            logging.info("Shutting down process %r", process)
            process.terminate()
            process.join()
logging.info("All done")

