function itemSearch(){
    const q = document.getElementById("searchInput").value;
    if(q.length >= 3) {
        const url = window.location.href +"item/search?q="+ q;
        const options = {method: "GET"};
        fetch(url, options)
            .then(function (response) {
                return response.json();
            })
            .then(function (elements) {
                let articlesDiv = document.getElementById("OffArticles");
                articlesDiv.innerHTML = '';
                let h2 = document.createElement("h2");
                h2.innerText = "Offer";
                articlesDiv.append(h2)
                articlesDiv = document.getElementById("ReqArticles");
                articlesDiv.innerHTML = '';
                h2 = document.createElement("h2");
                h2.innerText = "Request";
                articlesDiv.append(h2)
                for (let i = 0; i < elements.length; i++) {
                    if(elements[i]['ad'] === 'Offer')
                        articlesDiv = document.getElementById("OffArticles");
                    else
                        articlesDiv = document.getElementById("ReqArticles");
                    //article
                    const article = document.createElement("article");
                    article.classList.add("col");
                    //Card div
                    let cardDiv = document.createElement("div");
                    cardDiv.classList.add("card", "shadow-sm");
                    //img
                    let img = document.createElement("img");
                    img.src = "data:image/png;base64,"+ elements[i]['image'];
                    img.alt = elements[i]['title'];
                    cardDiv.append(img);
                    //card body
                    let bodyDiv = document.createElement("div");
                    bodyDiv.classList.add("card-body");
                    //item title
                    let h2 = document.createElement("h2");
                    let aTitle = document.createElement("a");
                    aTitle.text = elements[i]['title'];
                    aTitle.href = "/item/"+ elements[i]['id'];
                    h2.append(aTitle);
                    bodyDiv.append(h2);
                    //description
                    let pDesc = document.createElement("p");
                    pDesc.classList.add("card-text");
                    pDesc.innerText = elements[i]['description'];
                    bodyDiv.append(pDesc);
                    //date
                    let pDate = document.createElement("p");
                    pDate.classList.add("card-text");
                    pDate.innerText = "Data: ";
                    let time = document.createElement("time");
                    let date = elements[i]['date']
                    let format = new Date(date).getDate() +"-"+ (new Date(date).getMonth()+1) +"-"+ new Date(date).getFullYear()
                    time.dateTime = format;
                    time.innerText = format;
                    pDate.append(time);
                    bodyDiv.append(pDate);
                    //buttons
                    let divBtns = document.createElement("div");
                    divBtns.classList.add("d-flex", "justify-content-between", "align-items-center");
                    let divGroup = document.createElement("div");
                    divGroup.classList.add("btn-group");
                    let aCard = document.createElement("a");
                    aCard.classList.add("btn", "btn-sm", "btn-outline-primary");
                    aCard.setAttribute("role", "button");
                    aCard.href = "/item/"+ elements[i]['id'];
                    aCard.text = "Scheda";
                    divGroup.append(aCard);
                    //insert elements
                    divBtns.append(divGroup);
                    bodyDiv.append(divBtns);
                    cardDiv.append(bodyDiv);
                    article.append(cardDiv);
                    articlesDiv.append(article);

                }
            })
    }
}