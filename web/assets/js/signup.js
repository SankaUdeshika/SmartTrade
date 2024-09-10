async  function signup() {
    
    alert("Done");
    
    const user_dto = {
        first_name: document.getElementById("firstName").value,
        last_name: document.getElementById("lastName").value,
        email: document.getElementById("email").value,
        password: document.getElementById("password").value,
    };
       
    
    
    
    const response = await fetch("SignUp",
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
        console.log(json); // json object ekama print karagannawa
    } else {
        console.log("error");
    }
    
}


