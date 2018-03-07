import socket
import time
add = ("",9000)
sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
sock.bind(add)
def main():
    sock.listen(1)
    while True:
        conn, clien = sock.accept()
        try:
            while True:
                data = conn.recv(16)
                print 'received "%s"' % data
                if data:
                    print 'sending data back to the client'
                    conn.sendall(data)
                else:
                    print 'no more data from', clien
                    break

        finally:
        # Clean up the connection
            conn.close()
main()
