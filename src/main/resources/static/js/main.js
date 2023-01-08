$(document).ready(function () {

    $("#search-form").submit(function (event) {

        //stop submit the form, we will post it manually.
        event.preventDefault();

        fire_ajax_submit();

    });

});

function fire_ajax_submit() {

    var search = {}
    search["phone"] = $("#phone").val();

    $("#btn-search").prop("disabled", true);

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/country/resolve",
        data: JSON.stringify(search),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {

            var mainContainer = document.getElementById("feedback");
            mainContainer.innerHTML = "";

            var div = document.createElement("div");
            div.innerHTML = 'Phone code: ' + data.phoneCode;
            mainContainer.appendChild(div);

            for (var i = 0; i < data.countries.length; i++) {
                var div = document.createElement("div");
                div.innerHTML = 'Country: ' + data.countries[i].name;
                mainContainer.appendChild(div);
            }

            console.log("SUCCESS : ", data);
            $("#btn-search").prop("disabled", false);
        },
        error: function (e) {
            var json = JSON.parse(e.responseText);

            var mainContainer = document.getElementById("feedback");
            mainContainer.innerHTML = "";

            var div = document.createElement("div");
            div.innerHTML = 'Error: ' + json.msg;
            mainContainer.appendChild(div);

            console.log("ERROR : ", e);
            $("#btn-search").prop("disabled", false);

        }
    });

}