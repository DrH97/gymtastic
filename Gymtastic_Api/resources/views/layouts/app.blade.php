<!DOCTYPE html>
<html lang="en">

<head>
    @include('partials.head', ['title' => ''])
</head>


<body class="hold-transition skin-blue sidebar-mini theme-deep-orange">

<div id="wrapper">
    <!-- Page Loader -->
    <div class="page-loader-wrapper">
        <div class="loader">
            <div class="preloader">
                <div class="spinner-layer pl-red">
                    <div class="circle-clipper left">
                        <div class="circle"></div>
                    </div>
                    <div class="circle-clipper right">
                        <div class="circle"></div>
                    </div>
                </div>
            </div>
            <p>Please wait...</p>
        </div>
    </div>
    <!-- #END# Page Loader -->

@include('partials.topbar')
@include('partials.sidebar', ['page' => ''])

<!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Main content -->
        <section class="content">
            @if(isset($siteTitle))
                <h3 class="page-title">
                    {{ $siteTitle }}
                </h3>
            @endif

            <div class="row">
                <div class="col-md-12">

                    @yield('content')

                </div>
            </div>
        </section>
    </div>
</div>

{!! Form::open(['route' => 'logout', 'style' => 'display:none;', 'id' => 'logout']) !!}
<button type="submit">Logout</button>
{!! Form::close() !!}

<style>
    body {
        width: 100vw;
        overflow-x: hidden;
    }
    .legal {
        padding: 1em;
        background: white;
        width: calc(100% - 300px);
        text-align: center;
        float: right;
    }

    body.ls-closed .legal {
        width: 100%;
        float: none;
    }
</style>

@include('partials.footer');
@include('partials.scripts')
</body>
</html>