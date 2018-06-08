<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

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

        return view('home', compact('workouts', 'gymlocations', 'members', 'instructors'));
    }
}
