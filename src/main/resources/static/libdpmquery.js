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

    this.DPMASTER = 'dpmaster.deathmask.net:27950';
    this.QUERY_OPENARENA_DEFAULT = 'getservers 71 empty full demo';
    this.QUERY_XONOTIC_DEFAULT = 'getservers Xonotic 3 empty full';
    this.QUERY_QUAKE_3_ARENA_DEFAULT = 'getservers 68 empty full';
    this.serviceURL = document.location.href + 'api/v1/master/query/';

    console.log(this.serviceURL);

    this.queryMaster = function(address, query, successCallback, failCallback){
        var endpoint = address || this.DPMASTER;
        var request = query || this.QUERY_OPENARENA_DEFAULT;
        var success = successCallback || this.onSuccess;
        var failed = failCallback || this.onFailed;
        var targetURL = this.serviceURL + endpoint + '/' + escape(request);
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