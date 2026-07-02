// CHECK LOGIN STATUS ON PAGE LOAD
window.onload = function () {
    if (localStorage.getItem("isLoggedIn") === "true") {
        showUpload();
    }
};

function login() {
    var u = document.getElementById("username").value;
    var p = document.getElementById("password").value;

    if (u !== "" && p !== "") {
        localStorage.setItem("isLoggedIn", "true"); // SAVE LOGIN
        showUpload();
    } else {
        alert("Please enter username and password");
    }
}

function showUpload() {
    document.getElementById("loginSection").style.display = "none";

    var upload = document.getElementById("uploadSection");
    upload.style.display = "block";
    upload.classList.add("fade");

    document.getElementById("title").innerText = "Upload Question Paper";
}

function uploadQP() {
    var file = document.getElementById("fileInput").files[0];

    if (!file) {
        alert("Please select a file");
        return;
    }

    if (file.type === "application/pdf" || file.type.startsWith("image/")) {
        document.getElementById("message").innerText = "QP Uploaded Successfully!";
    } else {
        alert("Only PDF or images allowed");
    }
}

// OPTIONAL LOGOUT (if needed later)
function logout() {
    localStorage.removeItem("isLoggedIn");
    location.reload();
}