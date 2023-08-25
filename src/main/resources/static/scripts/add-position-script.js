function addPosition() {
    const positionsList = document.querySelector("#positionsList");
    const index = positionsList.children.length;

    const newLi = document.createElement("div");
    newLi.className = "input-group mb-3";

    const idInput = document.createElement("input");
    idInput.type = "hidden";
    idInput.name = "positions[" + index + "].id";
    newLi.appendChild(idInput);

    const valueInput = document.createElement("input");
    valueInput.type = "text";
    valueInput.name = "positions[" + index + "].name";
    valueInput.className = "form-control";
    valueInput.required = true;
    newLi.appendChild(valueInput);

    const removeButton = document.createElement("button");
    removeButton.type = "button";
    removeButton.className = "btn btn-danger";
    removeButton.innerText = "Remove";
    removeButton.setAttribute("onclick", "removePosition(" + index + ")");
    newLi.appendChild(removeButton);

    positionsList.appendChild(newLi);
}

function removePosition(index) {
    const positionsList = document.querySelector("#positionsList");
    const positionToRemove = positionsList.children[index];
    if (positionToRemove) {
        positionsList.removeChild(positionToRemove);
    }
}