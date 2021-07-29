var stats = {
    type: "GROUP",
name: "Global Information",
path: "",
pathFormatted: "group_missing-name-b06d1",
stats: {
    "name": "Global Information",
    "numberOfRequests": {
        "total": "194426",
        "ok": "104452",
        "ko": "89974"
    },
    "minResponseTime": {
        "total": "0",
        "ok": "780",
        "ko": "0"
    },
    "maxResponseTime": {
        "total": "84327",
        "ok": "74951",
        "ko": "84327"
    },
    "meanResponseTime": {
        "total": "12009",
        "ok": "14274",
        "ko": "9380"
    },
    "standardDeviation": {
        "total": "7942",
        "ok": "5925",
        "ko": "9092"
    },
    "percentiles1": {
        "total": "11139",
        "ok": "13677",
        "ko": "7569"
    },
    "percentiles2": {
        "total": "15807",
        "ok": "17675",
        "ko": "12277"
    },
    "percentiles3": {
        "total": "25080",
        "ok": "25303",
        "ko": "24392"
    },
    "percentiles4": {
        "total": "36953",
        "ok": "30885",
        "ko": "45779"
    },
    "group1": {
    "name": "t < 800 ms",
    "count": 1,
    "percentage": 0
},
    "group2": {
    "name": "800 ms < t < 1200 ms",
    "count": 9,
    "percentage": 0
},
    "group3": {
    "name": "t > 1200 ms",
    "count": 104442,
    "percentage": 54
},
    "group4": {
    "name": "failed",
    "count": 89974,
    "percentage": 46
},
    "meanNumberOfRequestsPerSecond": {
        "total": "648.087",
        "ok": "348.173",
        "ko": "299.913"
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
        "total": "194426",
        "ok": "104452",
        "ko": "89974"
    },
    "minResponseTime": {
        "total": "0",
        "ok": "780",
        "ko": "0"
    },
    "maxResponseTime": {
        "total": "84327",
        "ok": "74951",
        "ko": "84327"
    },
    "meanResponseTime": {
        "total": "12009",
        "ok": "14274",
        "ko": "9380"
    },
    "standardDeviation": {
        "total": "7942",
        "ok": "5925",
        "ko": "9092"
    },
    "percentiles1": {
        "total": "11140",
        "ok": "13677",
        "ko": "7560"
    },
    "percentiles2": {
        "total": "15806",
        "ok": "17675",
        "ko": "12276"
    },
    "percentiles3": {
        "total": "25076",
        "ok": "25303",
        "ko": "24392"
    },
    "percentiles4": {
        "total": "36953",
        "ok": "30885",
        "ko": "45779"
    },
    "group1": {
    "name": "t < 800 ms",
    "count": 1,
    "percentage": 0
},
    "group2": {
    "name": "800 ms < t < 1200 ms",
    "count": 9,
    "percentage": 0
},
    "group3": {
    "name": "t > 1200 ms",
    "count": 104442,
    "percentage": 54
},
    "group4": {
    "name": "failed",
    "count": 89974,
    "percentage": 46
},
    "meanNumberOfRequestsPerSecond": {
        "total": "648.087",
        "ok": "348.173",
        "ko": "299.913"
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
