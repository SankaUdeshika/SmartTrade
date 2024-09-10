async  function verifyAccount() {


    const dto = {
        verification: document.getElementById("verification").value,
    };


    alert("OK1");

    const response = await fetch("Verification",
            {
                method: "POST",
                body: JSON.stringify(dto),
                headers: {
                    "Content-type": "applicaiton/json"
                }
            }
    );




    if (response.ok) {
        const json = await response.json();
        if (json.success) {
            window.location = 'index.html';
        } else {
            document.getElementById("message").innerHTML = json.content;
        }

    } else {
        document.getElementById("message").innerHTML = "Please Try again Later";
        console.log("error");
    }

}





