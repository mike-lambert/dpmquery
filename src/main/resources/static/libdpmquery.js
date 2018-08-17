function DPMQuery(){
    // XHR cross-browser
    if (typeof XMLHttpRequest === 'undefined') {
        XMLHttpRequest = function () {
            try { return new ActiveXObject('Msxml2.XMLHTTP.6.0'); }
            catch (e) {}
            try { return new ActiveXObject('Msxml2.XMLHTTP.3.0'); }
            catch (e) {}
            try { return new ActiveXObject('Microsoft.XMLHTTP'); }
            catch (e) {}
            // Microsoft.XMLHTTP points to Msxml2.XMLHTTP and is redundant
            throw new Error('This browser does not support XMLHttpRequest.');
        };
    }

    var serviceURL = document.location.href + 'api/v1/master/query/';

    //console.log(serviceURL);

    this.queryMaster = function(objSettings, successCallback, failCallback){
        var defaults = {
            address: DPMQuery.DPMASTER,
            query: DPMQuery.QUERY_OPENARENA_DEFAULT,
            game: uuid(),
            sort: 'ping',
            limit: 10,
            maxPing: 200
        };
        var settings = objSettings || defaults;
        var endpoint = settings.address || defaults.address;
        var request = settings.query || defaults.query;
        var order = settings.sort || defaults.sort;
        var cut = settings.limit || defaults.limit;
        var threshold = settings.maxPing || defaults.maxPing;
        var success = successCallback || this.onSuccess;
        var failed = failCallback || this.onFailed;
        var targetURL = serviceURL + endpoint + '/' + escape(request);
        var game = settings.game || defaults.game;
        var xhr = new XMLHttpRequest();
        xhr.addEventListener('load', function(evt){
            success(this.endpoint, JSON.parse(this.responseText));
        });
        xhr.addEventListener('error', function(evt){
            failed(this.endpoint, evt);
        });
        xhr.endpoint = endpoint;
        if (objSettings.pinnedServers && objSettings.pinnedServers.length){
            var query = {
                sort: order,
                limit: cut,
                maxPing: threshold,
                game: game,
                pinnedServers : objSettings.pinnedServers
            };
            console.log('Query object:', query);
            console.log('sending request to ' + targetURL);
            xhr.open('POST', targetURL);
            xhr.setRequestHeader('Content-Type', 'application/json');
            xhr.send(JSON.stringify(query));
        } else {
            targetURL = targetURL + '?sort=' + escape(order)
                + '&limit=' + escape(cut)
                + '&maxPing=' + escape(threshold);
            targetURL = targetURL + '&game=' + game;
            console.log('sending request to ' + targetURL);
            xhr.open('GET', targetURL);
            xhr.send();
        }
    };

    this.onSuccess = function(address, data){
        console.log(address, data);
    };

    this.onFailed = function(address, exception){
        console.warn(address, exception)
    };
}

DPMQuery.DPMASTER = 'dpmaster.deathmask.net:27950';
DPMQuery.QUERY_OPENARENA_DEFAULT = 'getservers 71 empty full demo';
DPMQuery.QUERY_XONOTIC_DEFAULT = 'getservers Xonotic 3 empty full';
DPMQuery.QUERY_QUAKE_3_ARENA_DEFAULT = 'getservers 68 empty full';

function uuid() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
        var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
        return v.toString(16);
    });
}