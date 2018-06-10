@extends('layouts.app')

@include('partials.sidebar')

<section class="content">
    <div class="container-fluid">
        <div class="block-header">
            <h2>
                INSTRUCTORS | <button onclick="window.location = '/instructors/create'">CREATE NEW</button>
            </h2> 
        </div>
        <!-- Basic Examples -->
        <div class="row clearfix">
            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                <div class="card">
                    <div class="header">
                        <h2>
                               ALL INSTRUCTORS
                        </h2>
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
                        <div class="table-responsive">
                            <table class="display dtr-inline collapsed table table-bordered table-striped table-hover js-basic-example dataTable">
                                <thead>
                                    <tr>
                                        <th>Photo</th>
                                        <th>Name</th>
                                        <th>Contacts</th>
                                        <th>Email</th>
                                        <th>Gender</th>
                                        <th>Gym (if any)</th>
                                        <th colspan="2">Actions</th>
                                    </tr>
                                </thead>
                                <tfoot>
                                    <tr>
                                        <th>Photo</th>
                                        <th>Name</th>
                                        <th>Contacts</th>
                                        <th>Email</th>
                                        <th>Gender</th>
                                        <th>Gym (if any)</th>
                                        <th colspan="2">Actions</th>
                                    </tr>
                                </tfoot>
                               <tbody>
                                   @foreach ($instructors as $instructor)
                                   <tr>
                                       <td><img src="{{ asset($instructor->photo) }}" alt=""></td>
                                       <td>{{ $instructor->name }}</td>
                                       <td>{{ $instructor->contacts }}</td>
                                       <td>{{ $instructor->email }}</td>
                                       <td>{{ $instructor->gender }}</td>
                                       <td>{{ $instructor->gym }}</td>
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
    </div>

</section>