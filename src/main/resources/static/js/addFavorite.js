//Implementazione parzialmente funzionante,
// al caricamento dela pagine prende correttamenre se l'item è favorito o meno

const favorite = document.getElementById("favorite");
function addToFav(id) {
    const url = window.location.origin + "/item/" + id + "/addToFav";
    const options = {method: "GET"};
    fetch(url, options)
        .then(function (response) {
            console.log(response.json);
            if(response.body == true) {
                checkFavoriteButton(false);
            }
        })
}

function removeFromFav(id){
    const url = window.location.origin +"/item/"+ id +"/removeFromFav";
    const options = {method: "GET"};
    fetch(url, options)
        .then(function (response) {
            if(response.bodyUsed == true) {
                checkFavoriteButton(false);
            }
        })
}

function checkFavoriteButton(check, id){
    console.log("LOG");
    if(check === true){
        favorite.className = "btn btn-outline-danger";
        favorite.setAttribute("onclick", "addToFav("+ id +")");
        favorite.innerHTML = "Favorito";
        favorite.setAttribute("aria-label", "uno dei favoriti");
    }else{
        favorite.className = "btn btn-outline-secondary";
        favorite.setAttribute("onclick", "removeFromFav("+ id +")");
        favorite.innerHTML = "Aggiungi ai favoriti";
        favorite.setAttribute("aria-label", "aggiungi ai favoriti");
    }
    console.log(favorite);
}
