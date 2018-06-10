@extends('layouts.app', ['title' => 'Gym Locations', 'page' => 'gymlocations'])

<section class="content">
    <div class="container-fluid">
        <div class="block-header">
            <h2>
                GYM LOCATIONS | <button onclick="window.location = '/gyms/new'">CREATE NEW</button>
            </h2>
        </div>
        <!-- Basic Examples -->
        <div class="row clearfix">
            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                <div class="card">
                    <div class="header">
                        <h2>
                            MAP OF GYM LOCATIONS
                        </h2>
                    </div>
                    <div class="body">
                        {!! Mapper::render() !!}
                    </div>
                </div>

                <div class="card">
                    <div class="sbody">
                        <!-- Exportable Table -->
                        <div class="row clearfix">
                            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                <div class="card">
                                    <div class="header">
                                        <h2>
                                            ALL GYM LOCATIONS
                                        </h2>
                                        <ul class="header-dropdown m-r--5">
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
                                        </ul>
                                    </div>
                                    <div class="body">
                                        <div class="table-responsive">
                                            <table class="table stable-bordered table-striped table-hover dataTable js-exportable">
                                                <thead>
                                                    <tr>
                                                        <th>#</th>
                                                        <th>Name</th>
                                                        <th>Opening time</th>
                                                        <th>Closing time</th>
                                                        <th>Latitude</th>
                                                        <th>Longitude</th>
                                                        <th>Days (if any)</th>
                                                        <th colspan="2">#</th>
                                                    </tr>
                                                </thead>
                                                <tfoot>
                                                    <tr>
                                                        <th>#</th>
                                                        <th>Name</th>
                                                        <th>Opening time</th>
                                                        <th>Closing time</th>
                                                        <th>Latitude</th>
                                                        <th>Longitude</th>
                                                        <th>Days (if any)</th>
                                                        <th colspan="2">#</th>
                                                    </tr>
                                                </tfoot>
                                                <tbody>
                                                    @foreach ($gyms as $gym)                                                   
                                                    
                                                    <tr>
                                                        <td>{{ $gym->id }}</td>
                                                        <td>{{ $gym->name }}</td>
                                                        <td>{{ $gym->opening_time }}</td>
                                                        <td>{{ $gym->closing_time }}</td>
                                                        <td>{{ $gym->lat }}</td>
                                                        <td>{{ $gym->long }}</td>
                                                        <td>{{ $gym->days }}</td>
                                                        <td><a href="" style="color: bluegreen;"><i class="material-icons">edit</i></a></td>
                                                        <td><a href="" style="color: red;"><i class="material-icons">delete</i></a></td>
                                                    </tr>

                                                    @endforeach
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- #END# Exportable Table -->
            
                    </div>
                </div>
            </div>
        </div>
    </div>

</section>