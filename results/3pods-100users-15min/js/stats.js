var stats = {
    type: "GROUP",
name: "Global Information",
path: "",
pathFormatted: "group_missing-name-b06d1",
stats: {
    "name": "Global Information",
    "numberOfRequests": {
        "total": "167766",
        "ok": "167766",
        "ko": "0"
    },
    "minResponseTime": {
        "total": "410",
        "ok": "410",
        "ko": "-"
    },
    "maxResponseTime": {
        "total": "5406",
        "ok": "5406",
        "ko": "-"
    },
    "meanResponseTime": {
        "total": "534",
        "ok": "534",
        "ko": "-"
    },
    "standardDeviation": {
        "total": "187",
        "ok": "187",
        "ko": "-"
    },
    "percentiles1": {
        "total": "484",
        "ok": "484",
        "ko": "-"
    },
    "percentiles2": {
        "total": "548",
        "ok": "548",
        "ko": "-"
    },
    "percentiles3": {
        "total": "734",
        "ok": "734",
        "ko": "-"
    },
    "percentiles4": {
        "total": "1499",
        "ok": "1499",
        "ko": "-"
    },
    "group1": {
    "name": "t < 800 ms",
    "count": 160930,
    "percentage": 96
},
    "group2": {
    "name": "800 ms < t < 1200 ms",
    "count": 2879,
    "percentage": 2
},
    "group3": {
    "name": "t > 1200 ms",
    "count": 3957,
    "percentage": 2
},
    "group4": {
    "name": "failed",
    "count": 0,
    "percentage": 0
},
    "meanNumberOfRequestsPerSecond": {
        "total": "186.2",
        "ok": "186.2",
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
        "total": "167766",
        "ok": "167766",
        "ko": "0"
    },
    "minResponseTime": {
        "total": "410",
        "ok": "410",
        "ko": "-"
    },
    "maxResponseTime": {
        "total": "5406",
        "ok": "5406",
        "ko": "-"
    },
    "meanResponseTime": {
        "total": "534",
        "ok": "534",
        "ko": "-"
    },
    "standardDeviation": {
        "total": "187",
        "ok": "187",
        "ko": "-"
    },
    "percentiles1": {
        "total": "484",
        "ok": "484",
        "ko": "-"
    },
    "percentiles2": {
        "total": "548",
        "ok": "548",
        "ko": "-"
    },
    "percentiles3": {
        "total": "734",
        "ok": "734",
        "ko": "-"
    },
    "percentiles4": {
        "total": "1499",
        "ok": "1499",
        "ko": "-"
    },
    "group1": {
    "name": "t < 800 ms",
    "count": 160930,
    "percentage": 96
},
    "group2": {
    "name": "800 ms < t < 1200 ms",
    "count": 2879,
    "percentage": 2
},
    "group3": {
    "name": "t > 1200 ms",
    "count": 3957,
    "percentage": 2
},
    "group4": {
    "name": "failed",
    "count": 0,
    "percentage": 0
},
    "meanNumberOfRequestsPerSecond": {
        "total": "186.2",
        "ok": "186.2",
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
