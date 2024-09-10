async  function signIn() {

    const user_dto = {
        email: document.getElementById("email").value,
        password: document.getElementById("password").value,
    };




    const response = await fetch("SignIn",
            {
                method: "POST",
                body: JSON.stringify(user_dto),
                headers: {
                    "Content-type": "applicaiton/json"
                }
            }
    );

    if (response.ok) {
        //response eke ena JSON eka ganne meken
        const json = await response.json();
        if (json.success) {
            window.location = 'index.html';
        } else {
            if (json.content == "Unverified") {
                window.location = "verify-account.html"
            } else {
                document.getElementById("message").innerHTML = json.content;
            }
        }






    } else {
        document.getElementById("message").innerHTML = "Please Try again Later";
        console.log("error");
    }

}





