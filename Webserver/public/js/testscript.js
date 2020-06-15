function makeRequest (method, url, data) {
    return new Promise(function (resolve, reject) {
        var xhr = new XMLHttpRequest();
        xhr.open(method, url);
        xhr.onload = function () {
            if (this.status >= 200 && this.status < 300) {
                resolve(xhr.response);
            } else {
                reject({
                    status: this.status,
                    statusText: xhr.statusText
                });
            }
        };
        xhr.onerror = function () {
            reject({
                status: this.status,
                statusText: xhr.statusText
            });
        };
        if(method=="POST" && data){
            xhr.send(data);
        }else{
            xhr.send();
        }
    });
}

//GET example
;

function timedCount() {
    var results;
    makeRequest('GET', "/test-casus/js-worker/getdata").then(function(data){
        var results=JSON.parse(data);
        postMessage(results.name)
    })
    // repeat
    setTimeout("timedCount()",10000);
}

// initial call.
timedCount();

