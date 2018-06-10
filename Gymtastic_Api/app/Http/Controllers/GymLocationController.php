<?php

namespace App\Http\Controllers;

use App\GymLocation;
use Illuminate\Http\Request;

use Mapper;

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
        Mapper::map(8.7832, 25.5085, ['marker' => false, 'zoom' => 4,]);

        $gyms->each(function($gym) {
            $content = '<b>' . $gym->name . '</b><br>From: ' . $gym->opening_time . '<br>To: ' . $gym->closing_time;

            Mapper::informationWindow($gym->lat, $gym->long, $content, ['sopen' => true, 'maxWidth'=> 300, 'markers' => ['title' => 'Title', 'date' => 'Date']]);
        });
        
        return view('admin.gymlocations.index', compact('gyms'));
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create()
    {
        //
        return view('admin.gymlocations.create');
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
        // return $request;
        $name = $request->name;
        $opening_time = $request->opening_time;
        $closing_time = $request->closing_time;
        $lat = $request->lat;
        $long = $request->lon;

        $gym = GymLocation::create(compact('name', 'opening_time', 'closing_time', 'lat', 'long'));

        return $gym ? $this->index() : ['message' => 'Error Saving Gym Location']; 
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
     * @return \Illuminate\Http\Response
     */
    public function edit($gymLocation)
    {
        //
        $gym = GymLocation::find($gymLocation);
        return view('admin.gymlocations.edit', compact('gym'));
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $gymLocation)
    {
        //
        return $request . ' ' . $gymLocation;
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

        return $instructors ? $instructors : [null];
    }

    public function gymMembers($gymLocation) {
        $gym = GymLocation::find($gymLocation);

        $members = $gym->members;

        return $members;
    }
}
