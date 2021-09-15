var stats = {
    type: "GROUP",
name: "Global Information",
path: "",
pathFormatted: "group_missing-name-b06d1",
stats: {
    "name": "Global Information",
    "numberOfRequests": {
        "total": "18134",
        "ok": "18134",
        "ko": "0"
    },
    "minResponseTime": {
        "total": "45",
        "ok": "45",
        "ko": "-"
    },
    "maxResponseTime": {
        "total": "377",
        "ok": "377",
        "ko": "-"
    },
    "meanResponseTime": {
        "total": "164",
        "ok": "164",
        "ko": "-"
    },
    "standardDeviation": {
        "total": "33",
        "ok": "33",
        "ko": "-"
    },
    "percentiles1": {
        "total": "161",
        "ok": "161",
        "ko": "-"
    },
    "percentiles2": {
        "total": "182",
        "ok": "182",
        "ko": "-"
    },
    "percentiles3": {
        "total": "221",
        "ok": "220",
        "ko": "-"
    },
    "percentiles4": {
        "total": "258",
        "ok": "258",
        "ko": "-"
    },
    "group1": {
    "name": "t < 800 ms",
    "count": 18134,
    "percentage": 100
},
    "group2": {
    "name": "800 ms < t < 1200 ms",
    "count": 0,
    "percentage": 0
},
    "group3": {
    "name": "t > 1200 ms",
    "count": 0,
    "percentage": 0
},
    "group4": {
    "name": "failed",
    "count": 0,
    "percentage": 0
},
    "meanNumberOfRequestsPerSecond": {
        "total": "60.246",
        "ok": "60.246",
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
        "total": "18134",
        "ok": "18134",
        "ko": "0"
    },
    "minResponseTime": {
        "total": "45",
        "ok": "45",
        "ko": "-"
    },
    "maxResponseTime": {
        "total": "377",
        "ok": "377",
        "ko": "-"
    },
    "meanResponseTime": {
        "total": "164",
        "ok": "164",
        "ko": "-"
    },
    "standardDeviation": {
        "total": "33",
        "ok": "33",
        "ko": "-"
    },
    "percentiles1": {
        "total": "161",
        "ok": "161",
        "ko": "-"
    },
    "percentiles2": {
        "total": "182",
        "ok": "182",
        "ko": "-"
    },
    "percentiles3": {
        "total": "220",
        "ok": "220",
        "ko": "-"
    },
    "percentiles4": {
        "total": "258",
        "ok": "258",
        "ko": "-"
    },
    "group1": {
    "name": "t < 800 ms",
    "count": 18134,
    "percentage": 100
},
    "group2": {
    "name": "800 ms < t < 1200 ms",
    "count": 0,
    "percentage": 0
},
    "group3": {
    "name": "t > 1200 ms",
    "count": 0,
    "percentage": 0
},
    "group4": {
    "name": "failed",
    "count": 0,
    "percentage": 0
},
    "meanNumberOfRequestsPerSecond": {
        "total": "60.246",
        "ok": "60.246",
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
