@extends('layouts.app')

@include('partials.sidebar')

<section class="content">
    <div class="container-fluid">
        <div class="block-header">
            <h2>DASHBOARD</h2>
        </div>

        <!-- Widgets -->
        <div class="row clearfix">
            <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
                <a href="/workouts"><div class="info-box bg-pink hover-expand-effect">
                    <div class="icon">
                        <i class="material-icons">playlist_add_check</i>
                    </div>
                    <div class="content">
                        <div class="text">WORKOUTS</div>
                        <div class="number count-to" data-from="0" data-to="{{ $workouts }}" data-speed="1000" data-fresh-interval="200"></div>
                    </div>
                </div></a>
            </div>
            <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
                <a href="/gyms"><div class="info-box bg-cyan hover-expand-effect">
                    <div class="icon">
                        <i class="material-icons">help</i>
                    </div>
                    <div class="content">
                        <div class="text">GYMS</div>
                        <div class="number count-to" data-from="0" data-to="{{ $gymlocations }}" data-speed="1000" data-fresh-interval="20"></div>
                    </div>
                </div></a>
            </div>
            <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
                <a href="/instructors"><div class="info-box bg-light-green hover-expand-effect">
                    <div class="icon">
                        <i class="material-icons">forum</i>
                    </div>
                    <div class="content">
                        <div class="text">INSTRUCTORS</div>
                        <div class="number count-to" data-from="0" data-to="{{ $instructors }}" data-speed="1000" data-fresh-interval="20"></div>
                    </div>
                </div></a>
            </div>
            <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
                <a href="/users"><div class="info-box bg-orange hover-expand-effect">
                    <div class="icon">
                        <i class="material-icons">person_add</i>
                    </div>
                    <div class="content">
                        <div class="text">MEMBERS</div>
                        <div class="number count-to" data-from="0" data-to="{{ $members }}" data-speed="1000" data-fresh-interval="20"></div>
                    </div>
                </div></a>
            </div>
        </div>
        <!-- #END# Widgets -->
        <!-- CPU Usage -->
        {{-- <div class="row clearfix">
            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                <div class="card">
                    <div class="header">
                        <div class="row clearfix">
                            <div class="col-xs-12 col-sm-6">
                                <h2>CPU USAGE (%)</h2>
                            </div>
                            <div class="col-xs-12 col-sm-6 align-right">
                                <div class="switch panel-switch-btn">
                                    <span class="m-r-10 font-12">REAL TIME</span>
                                    <label>OFF<input type="checkbox" id="realtime" checked><span class="lever switch-col-cyan"></span>ON</label>
                                </div>
                            </div>
                        </div>
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
                        <div id="real_time_chart" class="dashboard-flot-chart"></div>
                    </div>
                </div>
            </div>
        </div> --}}
        <!-- #END# CPU Usage -->

        <div class="card">
                <div class="header">
                    <div class="row clearfix">
                        <div class="col-xs-12 col-sm-6">
                            <h2>Trends</h2>
                        </div>
                        <canvas id="myChart" width="400" height="100"></canvas>
                    </div>
                </div>
        </div>
    </div>
</section>

<!-- ChartJs -->
<script src="{{ url('plugins/chartjs/Chart.bundle.js') }}"></script>

<script>
        var ctx = document.getElementById("myChart").getContext('2d');
        var chart = new Chart(ctx, {
            type: 'line',
            data: {
                datasets: [{
                    label: "Workouts",
                    // backgroundColor: 'rgb(255, 99, 132)',
                    borderColor: 'rgb(99, 255, 132)',
                    data: [@foreach($workoutChartDatas as $workout)
                        { x: new Date('{{ $workout["date"] }}'),
                            y: '{{ $workout["count"] }}'
                        },
                        @endforeach],
                    },{
                    label: "Members",
                    // backgroundColor: 'rgb(255, 99, 132)',
                    borderColor: 'rgb(255, 150, 100)',
                    data: [@foreach($memberChartDatas as $member)
                        { x: new Date('{{ $member["date"] }}'),
                            y: '{{ $member["count"] }}'
                        },
                        @endforeach],
                    },{
                    label: "Gym Locations",
                    // backgroundColor: 'rgb(255, 99, 132)',
                    borderColor: 'rgb(99, 132, 255)',
                    data: [@foreach($gymLocationChartDatas as $gym)
                        { x: new Date('{{ $gym["date"] }}'),
                            y: '{{ $gym["count"] }}'
                        },
                        @endforeach
                    ],
                }]
            },
            options: {
                scales: {
                    xAxes: [{
                        type: 'time',
                        time: {
                            unit: 'day',
                            displayFormats: {
                                day: 'DD MMM'
                            }
                        }
                    }]
                }
            }
        })
       
        </script>