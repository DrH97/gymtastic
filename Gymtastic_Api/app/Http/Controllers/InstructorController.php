<?php

namespace App\Http\Controllers;

use App\Instructor;
use Illuminate\Http\Request;

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
        foreach ($instructors as $i) {
            $i['gym'] = $i->gymLocation->name;
            unset($i['gym_location']);
        }

        return view('admin.instructors.index', compact('instructors'));
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create()
    {
        //
        $gyms = \App\GymLocation::all();
        return view('admin.instructors.create', compact('gyms'));
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
        $validations = $request->validate([
            'photo' => 'sometimes|image|max:2048',
            'name' => 'required',
            'contact' => 'required|numeric',
            'email' => 'required|email|unique:instructors_92879',
            'gender' => 'required',
        ]);

        $path = $request->file('photo')->store('avatars/instructors');

        $instructor = new Instructor();
        $instructor->name = $request->name;
        $instructor->contacts = $request->contact;
        $instructor->email = $request->email;
        $instructor->photo = $path;
        $instructor->gender = $request->gender;
        $instructor->gym_location_id = $request->gymlocation;

        $instructor->save();

        return $this->index();
    }

    /**
     * Display the specified resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function show($instructor)
    {
        //
        return Instructor::find($instructor);
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
        
        $gym = $instructor->gymLocation;

        return $gym;
    }
}
