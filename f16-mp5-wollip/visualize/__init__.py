import http.server
import json
import webbrowser

def draw_map():
    """Write a JSON file containing inputs and load a visualization."""

    load_visualization('voronoi.html')

def load_visualization(url, base_url='http://localhost:8000/visualize/'):
    """Load the visualization located at URL."""
    server = start_threaded_server()
    webbrowser.open_new(base_url + url)
    try:
        server.join()
    except KeyboardInterrupt:
        print('\nKeyboard interrupt received, exiting.')

class SilentServer(http.server.SimpleHTTPRequestHandler):
    def log_message(self, format, *args):
        return

def start_server():
    server, handler = http.server.HTTPServer, SilentServer
    httpd = server(('', 8000), handler)
    sa = httpd.socket.getsockname()
    print('Serving HTTP on', sa[0], 'port', sa[1], '...')
    print('Type Ctrl-C to exit.')
    try:
        httpd.serve_forever()
    finally:
        httpd.server_close()

import threading
def start_threaded_server():
    thread = threading.Thread(target=start_server)
    thread.daemon = True
    thread.start()
    return thread
