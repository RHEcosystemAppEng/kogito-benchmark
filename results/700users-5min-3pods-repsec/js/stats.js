var stats = {
    type: "GROUP",
name: "Global Information",
path: "",
pathFormatted: "group_missing-name-b06d1",
stats: {
    "name": "Global Information",
    "numberOfRequests": {
        "total": "210000",
        "ok": "16583",
        "ko": "193417"
    },
    "minResponseTime": {
        "total": "516",
        "ok": "516",
        "ko": "1622"
    },
    "maxResponseTime": {
        "total": "68025",
        "ok": "62328",
        "ko": "68025"
    },
    "meanResponseTime": {
        "total": "20325",
        "ok": "7718",
        "ko": "21406"
    },
    "standardDeviation": {
        "total": "18714",
        "ok": "5359",
        "ko": "19052"
    },
    "percentiles1": {
        "total": "12567",
        "ok": "6927",
        "ko": "12796"
    },
    "percentiles2": {
        "total": "14909",
        "ok": "9961",
        "ko": "15176"
    },
    "percentiles3": {
        "total": "61649",
        "ok": "16553",
        "ko": "61763"
    },
    "percentiles4": {
        "total": "63835",
        "ok": "27908",
        "ko": "63967"
    },
    "group1": {
    "name": "t < 800 ms",
    "count": 384,
    "percentage": 0
},
    "group2": {
    "name": "800 ms < t < 1200 ms",
    "count": 88,
    "percentage": 0
},
    "group3": {
    "name": "t > 1200 ms",
    "count": 16111,
    "percentage": 8
},
    "group4": {
    "name": "failed",
    "count": 193417,
    "percentage": 92
},
    "meanNumberOfRequestsPerSecond": {
        "total": "600",
        "ok": "47.38",
        "ko": "552.62"
    }
},
contents: {
"req_http-post-order-120d8": {
        type: "REQUEST",
        name: "HTTP Post Order",
path: "HTTP Post Order",
pathFormatted: "req_http-post-order-120d8",
stats: {
    "name": "HTTP Post Order",
    "numberOfRequests": {
        "total": "210000",
        "ok": "16583",
        "ko": "193417"
    },
    "minResponseTime": {
        "total": "516",
        "ok": "516",
        "ko": "1622"
    },
    "maxResponseTime": {
        "total": "68025",
        "ok": "62328",
        "ko": "68025"
    },
    "meanResponseTime": {
        "total": "20325",
        "ok": "7718",
        "ko": "21406"
    },
    "standardDeviation": {
        "total": "18714",
        "ok": "5359",
        "ko": "19052"
    },
    "percentiles1": {
        "total": "12567",
        "ok": "6927",
        "ko": "12795"
    },
    "percentiles2": {
        "total": "14908",
        "ok": "9961",
        "ko": "15159"
    },
    "percentiles3": {
        "total": "61649",
        "ok": "16553",
        "ko": "61763"
    },
    "percentiles4": {
        "total": "63835",
        "ok": "27908",
        "ko": "63967"
    },
    "group1": {
    "name": "t < 800 ms",
    "count": 384,
    "percentage": 0
},
    "group2": {
    "name": "800 ms < t < 1200 ms",
    "count": 88,
    "percentage": 0
},
    "group3": {
    "name": "t > 1200 ms",
    "count": 16111,
    "percentage": 8
},
    "group4": {
    "name": "failed",
    "count": 193417,
    "percentage": 92
},
    "meanNumberOfRequestsPerSecond": {
        "total": "600",
        "ok": "47.38",
        "ko": "552.62"
    }
}
    }
}

}

function fillStats(stat){
    $("#numberOfRequests").append(stat.numberOfRequests.total);
    $("#numberOfRequestsOK").append(stat.numberOfRequests.ok);
    $("#numberOfRequestsKO").append(stat.numberOfRequests.ko);

    $("#minResponseTime").append(stat.minResponseTime.total);
    $("#minResponseTimeOK").append(stat.minResponseTime.ok);
    $("#minResponseTimeKO").append(stat.minResponseTime.ko);

    $("#maxResponseTime").append(stat.maxResponseTime.total);
    $("#maxResponseTimeOK").append(stat.maxResponseTime.ok);
    $("#maxResponseTimeKO").append(stat.maxResponseTime.ko);

    $("#meanResponseTime").append(stat.meanResponseTime.total);
    $("#meanResponseTimeOK").append(stat.meanResponseTime.ok);
    $("#meanResponseTimeKO").append(stat.meanResponseTime.ko);

    $("#standardDeviation").append(stat.standardDeviation.total);
    $("#standardDeviationOK").append(stat.standardDeviation.ok);
    $("#standardDeviationKO").append(stat.standardDeviation.ko);

    $("#percentiles1").append(stat.percentiles1.total);
    $("#percentiles1OK").append(stat.percentiles1.ok);
    $("#percentiles1KO").append(stat.percentiles1.ko);

    $("#percentiles2").append(stat.percentiles2.total);
    $("#percentiles2OK").append(stat.percentiles2.ok);
    $("#percentiles2KO").append(stat.percentiles2.ko);

    $("#percentiles3").append(stat.percentiles3.total);
    $("#percentiles3OK").append(stat.percentiles3.ok);
    $("#percentiles3KO").append(stat.percentiles3.ko);

    $("#percentiles4").append(stat.percentiles4.total);
    $("#percentiles4OK").append(stat.percentiles4.ok);
    $("#percentiles4KO").append(stat.percentiles4.ko);

    $("#meanNumberOfRequestsPerSecond").append(stat.meanNumberOfRequestsPerSecond.total);
    $("#meanNumberOfRequestsPerSecondOK").append(stat.meanNumberOfRequestsPerSecond.ok);
    $("#meanNumberOfRequestsPerSecondKO").append(stat.meanNumberOfRequestsPerSecond.ko);
}
