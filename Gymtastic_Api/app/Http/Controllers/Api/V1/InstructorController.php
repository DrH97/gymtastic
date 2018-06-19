<?php

namespace App\Http\Controllers\Api\V1;

use App\Instructor;
use Illuminate\Http\Request;
use App\Http\Controllers\Controller;

class InstructorController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        //
        $instructors = Instructor::all();

        $instructors = $instructors ? $instructors : [];

        $instructors = [
            "total_results" => count($instructors),
            "results" => $instructors
        ];
        return response()->json($instructors);
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
    public function show($instructor)
    {
        //
        $instructor = Instructor::find($instructor);

        $instructor = $instructor ? array($instructor) : [];
        
        $instructor = [
            "total_results" => count($instructor),
            "results" => $instructor
        ];

        return response()->json($instructor);
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  \App\Instructor  $instructor
     * @return \Illuminate\Http\Response
     */
    public function edit(Instructor $instructor)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \App\Instructor  $instructor
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, Instructor $instructor)
    {
        //
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  \App\Instructor  $instructor
     * @return \Illuminate\Http\Response
     */
    public function destroy(Instructor $instructor)
    {
        //
    }

    public function instructorGymLocation($instructor) {
        $instructor = Instructor::find($instructor);
        
        $gym = $instructor ? array($instructor->gymLocation) : [];

        $gym = [
            "total_results" => count($gym),
            "results" => $gym
        ];

        return response()->json($gym);
    }
}
