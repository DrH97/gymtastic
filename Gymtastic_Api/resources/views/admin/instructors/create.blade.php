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
        <div class="row clearfix">
            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                <div class="card">
                    <div class="header">
                        <h2>INSTRUCTOR CREATE</h2>
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
                        <!-- Inline Layout -->
                        @if (Session::has('message'))
                        <div class="alert alert-info">
                            <p>{{ Session::get('message') }}</p>
                        </div>
                        @endif
                        @if ($errors->count() > 0)
                            <div class="alert alert-danger">
                                <ul class="list-unstyled">
                                    @foreach($errors->all() as $error)
                                        <li>{{ $error }}</li>
                                    @endforeach
                                </ul>
                            </div>
                        @endif
                        
                        <form action="/instructors" method="post" enctype="multipart/form-data">
                            @csrf
                            <div class="row clearfix">
                                {{-- <div class="col-xs-12 dropzone" action="/instructors" id="frmFileUpload">
                                    <div class="dz-message">
                                        <div class="drag-icon-cph">
                                            <i class="material-icons">touch_app</i>
                                        </div>
                                        <h3>Click to add Photo (optional).</h3>
                                    </div>
                                </div> --}}
                                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                    <div class="form-group">
                                        <label for="photo">Photo</label>
                                        {{-- <div class="form-line"> --}}
                                        <input name="photo" type="file" class="form-control" placeholder="Select image" required value="{{ old('photo') }}">
                                        {{-- </div> --}}
                                    </div>
                                </div>
                                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                    <div class="form-group">
                                        <label for="name">Names</label>
                                        <div class="form-line">
                                            <input name="name" type="text" class="form-control" placeholder="Firstname LastName" required value="{{ old('name') }}">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
                                    <div class="form-group">
                                        <label for="contact">Contact</label>
                                        <div class="form-line">
                                            <input name="contact" type="number" class="form-control" placeholder="+254 700 200 400" required value="{{ old('contact') }}">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
                                    <div class="form-group">
                                        <div class="form-line">
                                            <label for="email">Email</label>
                                            <input name="email" type="email" class="form-control" placeholder="instructor@gymtastic.com" required value="{{ old('email') }}">
                                        </div>
                                    </div>
                                </div>
                                
                                <div class="col-xs-6">

                                        <label for="gender">Gender</label>
                                    <div class="form-group">
                                        {{-- <div class="form-line"> --}}
                                            <input name="gender" value="male" type="radio" id="radio_1" class="radio-col-light-blue with-gap" required value="{{ old('gender') }}"/>
                                            <label for="radio_1">Male</label>
                                            <input name="gender" value="female" type="radio" id="radio_2" class="radio-col-light-blue with-gap" required value="{{ old('gender') }}"/>
                                            <label for="radio_2">Female</label>
                                            <input name="gender" value="other" type="radio" id="radio_3" class="radio-col-light-blue with-gap" required value="{{ old('gender') }}"/>
                                            <label for="radio_3">Other</label>
                                        {{-- </div> --}}
                                    </div>
                                </div>
                                <style>
                                    .dropdown-menu {
                                        max-height: 300px;
                                    }
                                </style>
                                <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12" style="max-height:200px;">
                                    <div class="form-group">                                
                                        <label for="gymlocation"> Gym Location (optional)</label>
                                        <select class="form-control show-tick" data-live-search="true" name="gymlocation" value="{{ old('gymlocation') }}">
                                            <option value="0">Select Location</option>
                                            @foreach ($gyms as $gym)
                                            <option value="{{ $gym->id }}">{{ $gym->name }}</option>
                                            @endforeach
                                        </select>
                                    </div>
                                </div>
                                
                                <button type="submit" class="btn btn-primary m-t-15 waves-effect">CREATE</button>
                            </div>
                        </form>

                        {{-- <form action="/" id="frmFileUpload" class="dropzone" method="post" enctype="multipart/form-data">
                            <div class="dz-message">
                                <div class="drag-icon-cph">
                                    <i class="material-icons">touch_app</i>
                                </div>
                                <h3>Drop files here or click to upload.</h3>
                                <em>(This is just a demo dropzone. Selected files are <strong>not</strong> actually uploaded.)</em>
                            </div>
                            <div class="fallback">
                                <input name="file" type="file" multiple />
                            </div>
                        </form>
                      --}}
                <!-- #END# Inline Layout -->
                    </div>
                </div>
            </div>
        </div>
        <!-- #END# Basic Validation -->
    </div>
</div>

</section>
