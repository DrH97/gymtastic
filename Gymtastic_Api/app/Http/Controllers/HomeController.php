<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Carbon;

class HomeController extends Controller
{
    /**
     * Create a new controller instance.
     *
     * @return void
     */
    public function __construct()
    {
        $this->middleware('auth');
    }

    /**
     * Show the application dashboard.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $workouts = \App\Workout::count();
        $gymlocations = \App\GymLocation::count();
        $members = \App\User::count();
        $instructors = \App\Instructor::count();

        $memberChartDatas = \App\User::select([
            DB::raw('DATE(created_at) AS date'),
            DB::raw('COUNT(id) AS count'),
        ])
        ->whereBetween('created_at', [Carbon::now()->subDays(7), Carbon::now()])
        ->groupBy('date')
        ->orderBy('date', 'ASC')
        ->get()
        ->toArray();

        $workoutChartDatas = \App\Workout::select([
            DB::raw('DATE(created_at) AS date'),
            DB::raw('COUNT(id) AS count'),
        ])
        ->whereBetween('created_at', [Carbon::now()->subDays(7), Carbon::now()])
        ->groupBy('date')
        ->orderBy('date', 'ASC')
        ->get()
        ->toArray();

        $gymLocationChartDatas = \App\GymLocation::select([
            DB::raw('DATE(created_at) AS date'),
            DB::raw('COUNT(id) AS count'),
        ])
        ->whereBetween('created_at', [Carbon::now()->subDays(7), Carbon::now()])
        ->groupBy('date')
        ->orderBy('date', 'ASC')
        ->get()
        ->toArray();

        // return $gymLocationChartDatas;

        return view('home', compact('workouts', 'gymlocations', 'members', 'instructors', 'gymLocationChartDatas', 'memberChartDatas', 'workoutChartDatas'));
    }
}
