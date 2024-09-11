async function  LoadFeatures() {
    const response = await fetch("LoadFeatures");

    if (response.ok) {
        const json = await response.json();
        const categoryList = json.categoryList;
        const modelList = json.modelList;
        const colorList = json.colorList;
        const storageList = json.storageList;
        const conditionList = json.productCondition;

        loadSelect("categorySelect", categoryList, "name");
        loadSelect("modelSelect", modelList, "name");
        loadSelect("colorSelect", colorList, "name");
        loadSelect("storageSelect", storageList, "value");
        loadSelect("conditionSelect", conditionList, "name");

    } else {
        document.getElementById("message").innerHTML = "Please Try again Later";
        console.log("error");
    }
}


function  loadSelect(selectTagId, list, property) {
    const SelectTag = document.getElementById(selectTagId);
    list.forEach(item => {
        let optionTag = document.createElement("option");
        optionTag.value = item.id;
        optionTag.innerHTML = item[property];
        SelectTag.appendChild(optionTag);
    });

// Real Loop  - don't use this
//    const categorySelectTag = document.getElementById("categorySelect");
//    categoryList.forEach(category => {
//        let optionTag = document.createElement("option");
//        optionTag.value = category.id;
//        optionTag.innerHTML = category.name;
//        categorySelectTag.appendChild(optionTag);
//    });

}