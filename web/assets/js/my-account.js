var modelList;
async function  LoadFeatures() {
    const response = await fetch("LoadFeatures");
    if (response.ok) {
        const json = await response.json();
        const categoryList = json.categoryList;
        modelList = json.modelList;
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


function  updateModels() {
    let modelSelectTag = document.getElementById("modelSelect");
    modelSelectTag.length = 1;
    let SelectedCategoryID = document.getElementById("categorySelect").value;
    modelList.forEach(model => {
        if (model.category.id == SelectedCategoryID) {
            let optionTag = document.createElement("option");
            optionTag.value = model.id;
            optionTag.innerHTML = model.name;
            modelSelectTag.appendChild(optionTag);
        }
    });
}



async function productListing() {
    const categorySelect = document.getElementById("categorySelect");
    const modelSelect = document.getElementById("modelSelect");
    const title = document.getElementById("title");
    const description = document.getElementById("description");
    const storageSelect = document.getElementById("storageSelect");
    const colorSelect = document.getElementById("colorSelect");
    const conditionSelect = document.getElementById("conditionSelect");
    const price = document.getElementById("price");
    const qty = document.getElementById("qty");
    const image1 = document.getElementById("image1");
    const image2 = document.getElementById("image2");
    const image3 = document.getElementById("image3");

    const data = new FormData();
    data.append("categoryId", categorySelect.value);
    data.append("modelId", modelSelect.value);
    data.append("title", title.value);
    data.append("descripiton", description.value);
    data.append("storageId", storageSelect.value);
    data.append("colorId", colorSelect.value);
    data.append("ConditionId", conditionSelect.value);
    data.append("price", price.value);
    data.append("quantity", qty.value);
    data.append("image1", image1.files[0]);
    data.append("image2", image2.files[0]);
    data.append("image3", image3.files[0]);



    const response = await fetch("ProductListing", {method: "POST", body: data});

    if (response.ok) {
        const json = await response.json();

        console.log(json.content);

        if (json.success) {
            categorySelect.value = 0;
            modelSelect.level = 1;
            title.value = "";
            storageSelect.value = 0;
            colorSelect.value = 0;
            price.value = "";
            qty.value = 1;
            image1.files = null;
            image2.files = null;
            image3.files = null;

            document.getElementById("message").innerHTML = json.content;
            document.getElementById("message").className = "text-success";

        } else {
            document.getElementById("message").innerHTML = json.content;
        }

    } else {
        document.getElementById("message").innerHTML = "Please Try again Later";
        console.log("error");
    }


}