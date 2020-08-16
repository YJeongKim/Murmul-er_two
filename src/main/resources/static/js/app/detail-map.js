let markerImageOR = '/img/room/mk_or.png';
let markerImageTR = '/img/room/mk_tr.png';
let markerImageVI = '/img/room/mk_vi.png';
let markerImageOF = '/img/room/mk_of.png';
let markerImageAP = '/img/room/mk_ap.png';

var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
    mapOption = {
        center: new kakao.maps.LatLng(37.5559802396321, 126.972091251236), // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
    };

let map = new kakao.maps.Map(mapContainer, mapOption);
let ps = new kakao.maps.services.Places();

// 키워드로 장소 검색
ps.keywordSearch($("#address").text(), placesSearchCB);

// 키워드 검색 완료 시 호출되는 콜백함수
function placesSearchCB (result, status, pagination) {
    if (status === kakao.maps.services.Status.OK) {
        let bounds = new kakao.maps.LatLngBounds();
		displayMarker(result[0]);
        bounds.extend(new kakao.maps.LatLng(result[0].y, result[0].x));

        // 검색된 장소 위치를 기준으로 지도 범위를 재설정
        map.setBounds(bounds);
    }
}

// 지도에 마커를 표시하는 함수
function displayMarker(place) {
    // 마커를 생성하고 지도에 표시합니다
    let marker = new kakao.maps.Marker({
        map: map,
        position: new kakao.maps.LatLng(place.y, place.x) 
    });
    marker.setMap(map);
    let markerImageSrc;
    let roomType = $('#roomType').attr('value');
    if(roomType==="원룸") {
        markerImageSrc = markerImageOR;
    }
    else if(roomType==="투룸") {
        markerImageSrc = markerImageTR;
    }
    else if(roomType==="빌라") {
        markerImageSrc = markerImageVI;
    }
    else if(roomType==="오피스텔") {
        markerImageSrc = markerImageOF;
    }
    else if(roomType==="아파트") {
        markerImageSrc = markerImageAP;
    }
    let markerImage = new kakao.maps.MarkerImage(markerImageSrc, new kakao.maps.Size(50, 64), new kakao.maps.Point(13, 34));
    marker.setImage(markerImage);
}