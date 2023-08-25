const createInput = ({type, ...props}) => {
    const input = document.createElement("input");
    input.className = "form-control";
    input.type = type || "text";
    Object.keys(props).forEach(e => input[e] += " " + props[e]);
    return input;
}

function addPhone() {
    const phonesList = document.querySelector("#phonesList");
    const index = phonesList.children.length;

    const newLi = document.createElement("div");
    newLi.className = "input-group mb-3";

    const idInput = createInput({type: "hidden", nam: `phones[${index}].id`});
    newLi.appendChild(idInput);

    const countryCodeInput = createInput({
        name: `phones[${index}].countryCode`,
        className: "w-40",
        required: true,
        type: "number",
    })
    newLi.appendChild(countryCodeInput);

    const valueInput = createInput({
        name: `phones[${index}].value`, required: true,
        type: "number"
    });
    newLi.appendChild(valueInput);


    const removeButton = document.createElement("button");
    removeButton.type = "button";
    removeButton.className = "btn btn-danger";
    removeButton.innerText = "Remove";
    removeButton.setAttribute("onclick", "removePhone(" + index + ")");
    newLi.appendChild(removeButton);

    phonesList.appendChild(newLi);
}

function removePhone(index) {
    const phonesList = document.querySelector("#phonesList");
    const phoneToRemove = phonesList.children[index];
    if (phoneToRemove) {
        phonesList.removeChild(phoneToRemove);
    }
}
