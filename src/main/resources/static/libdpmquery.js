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

    console.log(serviceURL);

    this.queryMaster = function(objSettings, successCallback, failCallback){
        var defaults = {
            address: DPMQuery.DPMASTER,
            query: DPMQuery.QUERY_OPENARENA_DEFAULT,
            game: 'OpenArena',
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
        var targetURL = serviceURL + endpoint + '/' + escape(request)
            + '?sort=' + escape(order)
            + '&limit=' + escape(cut)
            + '&maxPing=' + escape(threshold);
        if (settings.game){
            targetURL = targetURL + '&game=' + settings.game;
        }
        var xhr = new XMLHttpRequest();
        xhr.addEventListener('load', function(evt){
            success(this.endpoint, JSON.parse(this.responseText));
        });
        xhr.addEventListener('error', function(evt){
            failed(this.endpoint, evt);
        });
        console.log('sending request to ' + targetURL);
        xhr.open('GET', targetURL);
        xhr.endpoint = endpoint;
        xhr.send();
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