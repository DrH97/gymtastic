<?php

namespace App\Http\Controllers\Api\V1;

use App\Workout;
use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use Illuminate\Validation\ValidationException;
use Illuminate\Support\Facades\Validator;

class WorkoutController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        //
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
        $validator = $this->validateWorkout($request);

        if ($validator->fails()) {
            return response()->json($validator->errors());
        }

        $user_id = $request->user_id;
        $location_id = $request->location_id;
        $workout_date = $request->workout_date;
        $exercise_type = $request->exercise_type;
        $reps = $request->reps;
        $sets = $request->sets;

        $workout = Workout::create(compact('user_id', 'location_id', 'workout_date', 'exercise_type', 'reps', 'sets'));

        return $workout ? $workout : ['message' => 'Error Saving Workout']; 
    }

    /**
     * Validate the user workout request.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return void
     */
    protected function validateWorkout(Request $request)
    {
        return Validator::make($request->all(), [
            'user_id' => 'required|int|exists:users_92879,id',
            'location_id' => 'sometimes|int|exists:gym_locations_92879,id|nullable',
            'workout_date' => 'required|date',
            'exercise_type' => 'required|string',
            'reps' => 'required|int|min:1',
            'sets' => 'required|int|min:1',
        ]);
    }

    /**
     * Display the specified resource.
     *
     * @param  \App\Workout  $workout
     * @return \Illuminate\Http\Response
     */
    public function show(Workout $workout)
    {
        //
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  \App\Workout  $workout
     * @return \Illuminate\Http\Response
     */
    public function edit(Workout $workout)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \App\Workout  $workout
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, Workout $workout)
    {
        //
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  \App\Workout  $workout
     * @return \Illuminate\Http\Response
     */
    public function destroy(Workout $workout)
    {
        //
    }
}
