var stats = {
    type: "GROUP",
name: "Global Information",
path: "",
pathFormatted: "group_missing-name-b06d1",
stats: {
    "name": "Global Information",
    "numberOfRequests": {
        "total": "141166",
        "ok": "141166",
        "ko": "0"
    },
    "minResponseTime": {
        "total": "413",
        "ok": "413",
        "ko": "-"
    },
    "maxResponseTime": {
        "total": "7770",
        "ok": "7770",
        "ko": "-"
    },
    "meanResponseTime": {
        "total": "636",
        "ok": "636",
        "ko": "-"
    },
    "standardDeviation": {
        "total": "352",
        "ok": "352",
        "ko": "-"
    },
    "percentiles1": {
        "total": "533",
        "ok": "533",
        "ko": "-"
    },
    "percentiles2": {
        "total": "582",
        "ok": "582",
        "ko": "-"
    },
    "percentiles3": {
        "total": "1438",
        "ok": "1438",
        "ko": "-"
    },
    "percentiles4": {
        "total": "2021",
        "ok": "2021",
        "ko": "-"
    },
    "group1": {
    "name": "t < 800 ms",
    "count": 125351,
    "percentage": 89
},
    "group2": {
    "name": "800 ms < t < 1200 ms",
    "count": 5489,
    "percentage": 4
},
    "group3": {
    "name": "t > 1200 ms",
    "count": 10326,
    "percentage": 7
},
    "group4": {
    "name": "failed",
    "count": 0,
    "percentage": 0
},
    "meanNumberOfRequestsPerSecond": {
        "total": "156.677",
        "ok": "156.677",
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
        "total": "141166",
        "ok": "141166",
        "ko": "0"
    },
    "minResponseTime": {
        "total": "413",
        "ok": "413",
        "ko": "-"
    },
    "maxResponseTime": {
        "total": "7770",
        "ok": "7770",
        "ko": "-"
    },
    "meanResponseTime": {
        "total": "636",
        "ok": "636",
        "ko": "-"
    },
    "standardDeviation": {
        "total": "352",
        "ok": "352",
        "ko": "-"
    },
    "percentiles1": {
        "total": "533",
        "ok": "533",
        "ko": "-"
    },
    "percentiles2": {
        "total": "582",
        "ok": "582",
        "ko": "-"
    },
    "percentiles3": {
        "total": "1438",
        "ok": "1438",
        "ko": "-"
    },
    "percentiles4": {
        "total": "2021",
        "ok": "2021",
        "ko": "-"
    },
    "group1": {
    "name": "t < 800 ms",
    "count": 125351,
    "percentage": 89
},
    "group2": {
    "name": "800 ms < t < 1200 ms",
    "count": 5489,
    "percentage": 4
},
    "group3": {
    "name": "t > 1200 ms",
    "count": 10326,
    "percentage": 7
},
    "group4": {
    "name": "failed",
    "count": 0,
    "percentage": 0
},
    "meanNumberOfRequestsPerSecond": {
        "total": "156.677",
        "ok": "156.677",
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
