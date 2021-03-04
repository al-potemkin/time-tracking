// <%---------------------------------------------seconds--------------------------------------------------------%>
if (countSecond > 0 && countSecond < 10) {
    document.getElementById("seconds").innerHTML = "0" + countSecond;
} else if (countSecond == 0 || countSecond == 60) {
    document.getElementById("seconds").innerHTML = "00";
} else {
    document.getElementById("seconds").innerHTML = countSecond;
}

setInterval(function () {
    myTimerSec()
}, 1000);

function myTimerSec() {
    if (countSecond == 59) {
        document.getElementById("seconds").innerHTML = "00";
        countSecond = 0;
        myTimerMin();
    } else if (countSecond < 9) {
        countSecond = countSecond + 1;
        document.getElementById("seconds").innerHTML = "0" + countSecond;
    } else if (countSecond < 59 && countSecond > 0) {
        countSecond = countSecond + 1;
        document.getElementById("seconds").innerHTML = countSecond;
    }
}

// <%---------------------------------------------minutes--------------------------------------------------------%>
if (countMinutes > 0 && countMinutes < 10) {
    document.getElementById("minutes").innerHTML = "0" + countMinutes;
} else if (countMinutes == 0 || countMinutes == 60) {
    document.getElementById("minutes").innerHTML = "00";
} else {
    document.getElementById("minutes").innerHTML = countMinutes;
}

function myTimerMin() {
    if (countMinutes == 59) {
        document.getElementById("minutes").innerHTML = "00";
        countMinutes = 0;
        myTimerHour();
    } else if (countMinutes < 9) {
        countMinutes = countMinutes + 1;
        document.getElementById("minutes").innerHTML = "0" + countMinutes;
    } else if (countMinutes < 59 && countMinutes > 0) {
        countMinutes = countMinutes + 1;
        document.getElementById("minutes").innerHTML = countMinutes;
    }
}

// <%---------------------------------------------hours--------------------------------------------------------%>
if (countHours > 0 && countHours < 10) {
    document.getElementById("hours").innerHTML = "0" + countHours;
} else if (countHours == 0 || countHours == 60) {
    document.getElementById("hours").innerHTML = "00";
} else {
    document.getElementById("hours").innerHTML = countHours;
}

function myTimerHour() {
    if (countHours == 59) {
        document.getElementById("hours").innerHTML = "00";
        countHours = 0;
    } else if (countHours < 9) {
        countHours = countHours + 1;
        document.getElementById("hours").innerHTML = "0" + countHours;
    } else if (countHours < 59 && countHours > 0) {
        countHours = countHours + 1;
        document.getElementById("hours").innerHTML = countHours;
    }
}