async function  loadProduct() {

    const parameters = new URLSearchParams(window.location.search);


    if (parameters.has("id")) {
        const productId = parameters.get("id");

        const response = await fetch("LoadSingleProduct?id=" + productId);
        if (response.ok) {

            const json = await  response.json();
            console.log(json.product.id);
            const id = json.product.id;
            
            
            document.getElementById("image1").src = "product-images/"+id+"/"+id+"image1.png";
            document.getElementById("image2").src = "product-images/"+id+"/"+id+"image2.png";
            document.getElementById("image3").src = "product-images/"+id+"/"+id+"image3.png";
            
            
            document.getElementById("leftimage1").src = "product-images/"+id+"/"+id+"image1.png";
            document.getElementById("leftimage2").src = "product-images/"+id+"/"+id+"image2.png";
            document.getElementById("leftimage3").src = "product-images/"+id+"/"+id+"image3.png";


        } else { 
            window.location = "index.html";
        }

    } else {
        window.location = "index.html";
    }





}