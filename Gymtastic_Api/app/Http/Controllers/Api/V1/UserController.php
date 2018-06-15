<?php

namespace App\Http\Controllers\Api\V1;

use App\User;
use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use Illuminate\Validation\ValidationException;
use Illuminate\Support\Facades\Validator;
use Illuminate\Support\Facades\Auth;

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

        $workouts = $member->workouts;

        if ($workouts != null)
            $response = [
                'status' => 'success',
                'total_results' => count($workouts),
                'results' => $workouts,
            ];
        else 
            $response = [
                'status' => 'success',
                'total_results' => 0,
            ];

        return response() ->json($response);
    }

     /**
     * Handle a login request to the application.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\RedirectResponse|\Illuminate\Http\Response|\Illuminate\Http\JsonResponse
     *
     * @throws \Illuminate\Validation\ValidationException
     */
    public function login(Request $request)
    {
        $validator = $this->validateLogin($request);

        if ($validator->fails()) {
            return response()->json($validator->errors());
        }
        
        if ($this->authenticate($request)){
            $response = [
                'status' => 'success',
                'message' => 'successful authentication',
                'user' => Auth::user(),
            ];
        } else {
            $response = [
                'status' => 'fail',
                'message' => 'Invalied credentials',
            ];
        }
        
        return response()->json($response);
    }

    /**
     * Validate the user login request.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return void
     */
    protected function validateLogin(Request $request)
    {
        $validation = Validator::make($request->all(), [
            $this->username() => 'required|string|email',
            'password' => 'required|string',
        ]);

        return $validation;
    }

    /**
     * Get the login username to be used by the controller.
     *
     * @return string
     */
    public function username()
    {
        return 'email';
    }

    public function authenticate(Request $request)
    {
        $credentials = $request->only('email', 'password');

        if (Auth::attempt($credentials)) {
            // Authentication passed...
            return true;
        }
    }

    /**
     * Log the user out of the application.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function logout(Request $request)
    {        
        Auth::logout();

        $response = [
            'status' => 'success',
            'message' => 'successful Logging out',
            'user' => Auth::user(),
        ];

        return response() ->json($response);
    }
}
