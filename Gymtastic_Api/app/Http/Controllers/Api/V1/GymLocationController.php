<?php

namespace App\Http\Controllers\Api\V1;

use App\Http\Controllers\Controller;
use App\GymLocation;
use Illuminate\Http\Request;

class GymLocationController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        //
        $gyms = GymLocation::all();
        return $gyms;
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create()
    {
        //
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
     * Display the specified resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function show($gymLocation)
    {
        //
        $gym = GymLocation::find($gymLocation);
        // $gyms = $gymLocation;
        return $gym;
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  \App\GymLocation  $gymLocation
     * @return \Illuminate\Http\Response
     */
    public function edit(GymLocation $gymLocation)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \App\GymLocation  $gymLocation
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, GymLocation $gymLocation)
    {
        //
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  \App\GymLocation  $gymLocation
     * @return \Illuminate\Http\Response
     */
    public function destroy(GymLocation $gymLocation)
    {
        //
    }

    public function gymInstructors($gymLocation) {
        $gym = GymLocation::find($gymLocation);

        $instructors = $gym->instructors;

        return $instructors;
    }

    public function gymMembers($gymLocation) {
        $gym = GymLocation::find($gymLocation);

        $members = $gym->members;

        return $members;
    }
}
