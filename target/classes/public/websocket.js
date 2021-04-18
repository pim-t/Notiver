window.onload = setupWebSocket;
window.onhashchange = setupWebSocket;


function setupWebSocket() {
    const textArea = document.querySelector("textarea");
    const ws = new WebSocket(`ws://localhost:7000/info`);

    document.getElementById("send").addEventListener("click", () => ws.send(textArea.value))

    ws.onclose = setupWebSocket; // should reconnect if connection is closed
}


