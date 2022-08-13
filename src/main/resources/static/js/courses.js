function activeCheck() {
    document.getElementById("gradeField").style.display = "none";
}

function passedCheck() {
    document.getElementById("gradeField").style.display = "block";
}

function editCourse(course) {
    console.log(course);
}

function function1(name, professor, LP, status, grade, id) {
    var nameInput = document.getElementById("nameInput" + id.toString());
    var professorInput = document.getElementById("professorInput" + id.toString());
    var LPInput = document.getElementById("LPInput" + id.toString());
    nameInput.value = name;
    professorInput.value = professor;
    LPInput.value = LP;
}

function showGradeField(id) {
    document.getElementById("gradeField" + id.toString()).style.display = "block";
}

function hideGradeField(id) {
    document.getElementById("gradeField" + id.toString()).style.display = "none";
}

function changeForm() {
    var a = document.getElementById("addCourse").style.display;
    if (a == "block") {
        document.getElementById("addCourse").style.display = "none";
    }
    else {
        document.getElementById("addCourse").style.display = "block";
    }
}
