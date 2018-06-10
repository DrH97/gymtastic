@extends('layouts.app', ['title' => 'Add Gym Location', 'page' => 'gymlocations-add'])

{{-- @include('partials.sidebar') --}}

<style>
    #map-canvas{
        width: 100%;
        height: 300px;
    }
</style>

<section class="content">
    <div class="container-fluid">
        {{-- <div class="block-header">
            <h2>GYM</h2>
        </div> --}}

        <!-- Basic Validation -->
        <div class="row clearfix">
            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                <div class="card">
                    <div class="header">
                        <h2>GYM CREATE</h2>
                        {{-- <ul class="header-dropdown m-r--5">
                            <li class="dropdown">
                                <a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                                    <i class="material-icons">more_vert</i>
                                </a>
                                <ul class="dropdown-menu pull-right">
                                    <li><a href="javascript:void(0);">Action</a></li>
                                    <li><a href="javascript:void(0);">Another action</a></li>
                                    <li><a href="javascript:void(0);">Something else here</a></li>
                                </ul>
                            </li>
                        </ul> --}}
                    </div>
                    <div class="body">
                    <form id="form_validation" action="/gyms/{{$gym->id}}" smethod="POST">
                            {{ csrf_field() }}
                            {{ method_field('PUT') }}
                            <div class="form-group form-float">
                                <div class="form-line">
                                <input type="text" class="form-control" name="name" required value="{{ $gym->name }}">
                                    <label class="form-label">Gym Name</label>
                                </div>
                            </div>
                            {{-- <div class="form-group form-float scol-md-3">
                                <b>Time (24 hour)</b>
                                <div class="input-group">
                                    <span class="input-group-addon">
                                        <i class="material-icons">access_time</i>
                                    </span>
                                    <div class="form-line">
                                        <input type="text" class="timepicker form-control time24" placeholder="Ex: 23:59">
                                    </div>
                                </div>
                            </div> --}}
                            <div class="form-group form-float">
                                <div class="form-group">
                                    <div class="form-line">
                                        <input id="time" type="time" class="timepicker form-control" name="opening_time" required  value="{{ $gym->opening_time }}">
                                        <label class="form-label" style="padding-left: 3em;">Please choose a Closing  time... Ex: 08:59</label>
                                    </div>
                                </div>
                            </div>
                            
                            <div class="form-group form-float">
                                <div class="form-line">
                                    <input type="time" class="timepicker form-control time24" name="closing_time" required value="{{ $gym->closing_time }}">
                                    <label class="form-label" style="padding-left: 3em;">Please choose a Closing  time... Ex: 17:59</label>
                                </div>
                            </div>
                            {{-- <div class="form-group form-float">
                                <div class="form-line">
                                        <input type="text" class="timepicker form-control time24">
                                    <label class="form-label">17:59</label>
                                </div>
                            </div> --}}
                            <div class="form-group">
                                <div class="form-line">
                                    <label for="">Map</label>
                                    <input type="text" id="searchmap">
                                </div>
                                <div id="map-canvas"></div>
                            </div>

                            <div class="form-group form-float">
                                <div class="form-line">
                                    <input type="number" class="form-control" name="lat" id="lat" step="any" required value="{{ $gym->lat }}">
                                    <label class="form-label">Latitude</label>
                                </div>
                            </div>
                            <div class="form-group form-float">
                                <div class="form-line">
                                    <input type="number" class="form-control" name="lon" id="lng" step="any" required value="{{ $gym->long }}">
                                    <label class="form-label">Longitude</label>
                                </div>
                            </div>
                            <button class="btn btn-primary waves-effect" type="submit">SUBMIT</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <!-- #END# Basic Validation -->
    </div>
</div>

</section>



 {{-- <div class="row clearfix">
            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                <div class="card">
                    <div class="header">
                        <h2>GYM CREATE</h2>
                    </div>
                    <div class="body">
                        <div class="container">
                                <div sclass="col-sm-12">
                                    <h1>Add Gym, Location</h1>
                                    {{Form::open(array('url'=>'/gyms/add', 'files'=>true))}}
                                        <div class="form-group">
                                            <label for="">Title</label>
                                            <input type="text" class="form-control" name="title" style="border-bottom: 1px solid #ddd;" placeholder="Gym Title" required>
                                        </div>
                            
                                        <div class="form-group">
                                            <label for="">Map</label>
                                            <input type="text" id="searchmap">
                                            <div id="map-canvas"></div>
                                        </div>
                            
                                        <div class="form-group">
                                            <label for="">Lat</label>
                                            <input type="text" class="form-control input-sm" name="lat" id="lat" style="border-bottom: 1px solid #ddd;" placeholder="Latitude" required>
                                        </div>
                            
                                        <div class="form-group">
                                            <label for="">Lng</label>
                                            <input type="text" class="form-control input-sm" name="lng" id="lng" style="border-bottom: 1px solid #ddd;" placeholder="Longitude" required>
                                        </div>
                            
                                        <button class="btn btn-sm btn-danger">Save</button>
                                    {{Form::close()}}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div> --}}


{{-- Google Maps API --}}
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCyB6K1CFUQ1RwVJ-nyXxd6W0rfiIBe12Q&libraries=places"
      type="text/javascript"></script>

<script>
    var map = new google.maps.Map(document.getElementById('map-canvas'),{
        center:{
            lat: {{ $gym->lat }},
            lng: {{ $gym->long }}
        },
        zoom:15
    });
    var marker = new google.maps.Marker({
        position: {
            lat: {{ $gym->lat }},
            lng: {{ $gym->long }}
        },
        map: map,
        draggable: true
    });
    var searchBox = new google.maps.places.SearchBox(document.getElementById('searchmap'));
    google.maps.event.addListener(searchBox,'places_changed',function(){
        var places = searchBox.getPlaces();
        var bounds = new google.maps.LatLngBounds();
        var i, place;
        for(i=0; place=places[i];i++){
                bounds.extend(place.geometry.location);
                marker.setPosition(place.geometry.location); //set marker position new...
            }
            map.fitBounds(bounds);
            map.setZoom(15);
    });
    google.maps.event.addListener(marker,'position_changed',function(){
        var lat = marker.getPosition().lat();
        var lng = marker.getPosition().lng();
        $('#lat').val(lat);
        $('#lng').val(lng);
    });
</script>