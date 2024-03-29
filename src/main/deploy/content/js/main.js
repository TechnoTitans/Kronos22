let disable = false;
let shootnow = false;
let shootBtn;
let sliderpos = 0;
var bigx = 0;
var bigy = 0;

async function sendDrive(x, y) {
    let data = {
        x: x,
        y: y,
    };

    $.ajax({
        url: "/drive",
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify(data),
        type: "POST",
        success: function (result) {
            //it worked
        }, error: function (result) {
            // console.log(result); Returns some stuff but idk what it means
        }
    });
}

async function sendShoot(shoot) {
    let data = {
        shoot: shoot,
    };

    $.ajax({
        url: "/shoot",
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify(data),
        type: "POST",
        success: function (result) {
            //it worked
        }, error: function (result) {
            // console.log(result); Returns some stuff but idk what it means
        }
    });

    if (shootnow) {
        shootnow = false;
        shootBtn.classList.remove("disabled")
    }
}

async function sendTilt(tilt) {
    let data = {
        tiltangle: tilt,
    };

    $.ajax({
        url: "/tilt",
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify(data),
        type: "POST",
        success: function (result) {
            //it worked
        }, error: function (result) {
            // console.log(result); Returns some stuff but idk what it means
        }
    });
}

async function sendTime(time) {
    let data = {
        shottime: time,
    };

    $.ajax({
        url: "/time",
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify(data),
        type: "POST",
        success: function (result) {
            //it worked
        }, error: function (result) {
            // console.log(result); Returns some stuff but idk what it means
        }
    });
}

async function sendMode(disable) {
    let data = {
        disabled: disable
    };

    $.ajax({
        url: "/mode",
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify(data),
        type: "POST",
        success: function (result) {
            //it worked
        }, error: function (result) {
            // console.log(result); Returns some stuff but idk what it means
        }
    });
}

window.addEventListener("click", function(e) {
    // Make page to full screen to allow app to fit
    if (!document.fullscreenElement) { // remove one ! for later
        document.documentElement.requestFullscreen({"navigationUI": "hide"}).catch(err => { alert(`Error attempting to enable fullscreen mode: ${err.message} (${err.name})`) });
    }
});

function onTimingChange(timein) {
    sendTime(timein.value)
}

function onSliderChange(slider) {
    slider.value = 0
    sliderpos = 0;
    sendTilt(sliderpos)
}

function currentSliderVal(slider) {
    sliderpos = slider.value
    // ***** I dont think we need this anymore *******
    // let x, y;
    // let angle = Math.atan2((coord.y - y_orig), (coord.x - x_orig));

    // if (Math.abs(coord.x) >= 800) {
    //     x = 0
    //     y = 0
    // } else if (is_it_in_the_circle()) {
    //     x = coord.x;
    //     y = coord.y;
    // }
    // else {
    //     x = radius * Math.cos(angle) + x_orig;
    //     y = radius * Math.sin(angle) + y_orig;
    // }

    sendTilt(sliderpos);
    while (sliderpos === 0) sendTilt(0);
}

function ontoggleRobotbtn(btn) {
    if (disable) {
        disable = false;
        btn.innerHTML = "Disabled"
        btn.classList.remove("btn-success");
        btn.classList.add("btn-danger");

    } else {
        disable = true;
        btn.innerHTML = "Enabled"
        btn.classList.remove("btn-danger");
        btn.classList.add("btn-success");
    }
    sendMode(disable)
};

async function onshootbtn(btn) {
    if (!shootnow) {
        shootnow = true;
        btn.classList.add("disabled");
        shootBtn = btn;
        sendShoot(shootnow);
    }
};

document.addEventListener('contextmenu', event => event.preventDefault());

screen.orientation.addEventListener("change", async () => {
    await new Promise(r => setTimeout(r, 600));
    if (!screen.orientation.type.toLowerCase().includes("landscape")) {
        location.reload();
    }
});

let canvas, ctx;
window.addEventListener('load', () => {

    if (!screen.orientation.type.toLowerCase().includes("landscape")) {
        document.getElementById("body").innerHTML = "";
        alert("Please rotate your device to landscape mode");
        return;
    }

    canvas = document.getElementById('joystick');
    ctx = canvas.getContext('2d');
    resize();

    // document.addEventListener('mousemove', Draw);

    document.addEventListener('touchstart', startDrawing);
    document.addEventListener('touchend', stopDrawing);
    document.addEventListener('touchcancel', stopDrawing);
    document.addEventListener('touchmove', Draw);
    window.addEventListener('resize', resize);
    setInterval(function() {sendDrive(bigx, bigy);}, 100);
});

let width, height, radius, x_orig, y_orig;
function resize() {
    width = window.innerWidth;
    radius = 75;
    height = radius * 6.5;
    ctx.canvas.width = width;
    ctx.canvas.height = height;
    background();
    joystick(width / 2, height / 3);
}

function background() {
    x_orig = width / 2;
    y_orig = height / 3;

    ctx.beginPath();
    ctx.arc(x_orig, y_orig, radius + 20, 0, Math.PI * 2, true);
    ctx.fillStyle = '#D3D3D3';
    ctx.fill();
}

function joystick(width, height) {
    ctx.beginPath();
    ctx.arc(width, height, radius, 0, Math.PI * 2, true);
    ctx.fillStyle = '#2234A8';
    ctx.fill();
    ctx.strokeStyle = '#659BDF';
    ctx.lineWidth = 8;
    ctx.stroke();
}

let coord = { x: 0, y: 0 };
let paint = false;

function getPosition(event) {
    if (event != null) {
        let mouse_x = event.clientX || event.touches[0].clientX;
        let mouse_y = event.clientY || event.touches[0].clientY;
        coord.x = mouse_x - canvas.offsetLeft;
        coord.y = mouse_y - canvas.offsetTop;
    }
}

function is_it_in_the_circle() {
    let current_radius = Math.sqrt(Math.pow(coord.x - x_orig, 2) + Math.pow(coord.y - y_orig, 2));
    return radius >= current_radius;
}

function startDrawing(event) {
    paint = true;
    getPosition(event);
    if (is_it_in_the_circle()) {
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        background();
        joystick(coord.x, coord.y);
        Draw();
    }
}

function stopDrawing() {
    paint = false;
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    background();
    joystick(width / 2, height / 3);
    coord.x = 0
    coord.y = 0
    bigx = 0;
    bigy = 0;
    sendDrive(0, 0);
}

function Draw(event) {
    if (paint) {
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        background();
        let x, y;
        let angle = Math.atan2((coord.y - y_orig), (coord.x - x_orig));

        if (Math.abs(coord.x) >= 600) {
            joystick(x_orig, y_orig);
            return;
        } else if (is_it_in_the_circle()) {
            joystick(coord.x, coord.y);
            x = coord.x;
            y = coord.y;
        }
        else {
            x = radius * Math.cos(angle) + x_orig;
            y = radius * Math.sin(angle) + y_orig;
            joystick(x, y);
        }
            getPosition(event);

            let x_relative = Math.round(x_orig - x);
            let y_relative = Math.round(y_orig - y);
            bigx = x_relative;
            bigy = y_relative;
            // sendDrive(x_relative, y_relative);
    }
}