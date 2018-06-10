<?php

namespace App\Http\Controllers;

use App\User;
use Illuminate\Http\Request;

class UserController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        //
        $users = User::all();
        return view('admin.users.index', compact('users'));
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        //
    }

    /**
     * Display the form for creating the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create()
    {
        //
        $gyms = \App\GymLocation::all();
        return view('admin.users.create', compact('gyms'));
    }

    /**
     * Display the specified resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function show($user)
    {
        //
        return User::find($user);
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \App\User  $user
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, User $user)
    {
        //
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  \App\User  $user
     * @return \Illuminate\Http\Response
     */
    public function destroy(User $user)
    {
        //
    }

    public function memberGymLocation($member) {
        $member =  User::find($member);
        
        return $member ? ($member->memberGymLocation ? $member->memberGymLocation : []) : [null];
        
    }

    public function memberWorkouts($member) {
        $member =  User::find($member);
        
        return $member ? ($member->workouts ? $member->workouts : []) : [null];
    }
}
