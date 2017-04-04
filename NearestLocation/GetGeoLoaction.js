/**
 * Created by kunalkrishna on 4/4/17.
 */
window.onload = function() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(UserLocation);
    }
    else
        NearestCity(38.8951, -72.0367);
}

function UserLocation(position) {
    NearestCity(position.coords.latitude, position.coords.longitude);
}
