var stats = {
    type: "GROUP",
name: "Global Information",
path: "",
pathFormatted: "group_missing-name-b06d1",
stats: {
    "name": "Global Information",
    "numberOfRequests": {
        "total": "50000",
        "ok": "50000",
        "ko": "0"
    },
    "minResponseTime": {
        "total": "413",
        "ok": "413",
        "ko": "-"
    },
    "maxResponseTime": {
        "total": "8454",
        "ok": "8454",
        "ko": "-"
    },
    "meanResponseTime": {
        "total": "608",
        "ok": "608",
        "ko": "-"
    },
    "standardDeviation": {
        "total": "729",
        "ok": "729",
        "ko": "-"
    },
    "percentiles1": {
        "total": "448",
        "ok": "448",
        "ko": "-"
    },
    "percentiles2": {
        "total": "526",
        "ok": "526",
        "ko": "-"
    },
    "percentiles3": {
        "total": "986",
        "ok": "986",
        "ko": "-"
    },
    "percentiles4": {
        "total": "5690",
        "ok": "5690",
        "ko": "-"
    },
    "group1": {
    "name": "t < 800 ms",
    "count": 46697,
    "percentage": 93
},
    "group2": {
    "name": "800 ms < t < 1200 ms",
    "count": 1412,
    "percentage": 3
},
    "group3": {
    "name": "t > 1200 ms",
    "count": 1891,
    "percentage": 4
},
    "group4": {
    "name": "failed",
    "count": 0,
    "percentage": 0
},
    "meanNumberOfRequestsPerSecond": {
        "total": "83.056",
        "ok": "83.056",
        "ko": "-"
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
        "total": "50000",
        "ok": "50000",
        "ko": "0"
    },
    "minResponseTime": {
        "total": "413",
        "ok": "413",
        "ko": "-"
    },
    "maxResponseTime": {
        "total": "8454",
        "ok": "8454",
        "ko": "-"
    },
    "meanResponseTime": {
        "total": "608",
        "ok": "608",
        "ko": "-"
    },
    "standardDeviation": {
        "total": "729",
        "ok": "729",
        "ko": "-"
    },
    "percentiles1": {
        "total": "448",
        "ok": "448",
        "ko": "-"
    },
    "percentiles2": {
        "total": "526",
        "ok": "526",
        "ko": "-"
    },
    "percentiles3": {
        "total": "986",
        "ok": "986",
        "ko": "-"
    },
    "percentiles4": {
        "total": "5690",
        "ok": "5690",
        "ko": "-"
    },
    "group1": {
    "name": "t < 800 ms",
    "count": 46697,
    "percentage": 93
},
    "group2": {
    "name": "800 ms < t < 1200 ms",
    "count": 1412,
    "percentage": 3
},
    "group3": {
    "name": "t > 1200 ms",
    "count": 1891,
    "percentage": 4
},
    "group4": {
    "name": "failed",
    "count": 0,
    "percentage": 0
},
    "meanNumberOfRequestsPerSecond": {
        "total": "83.056",
        "ok": "83.056",
        "ko": "-"
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
