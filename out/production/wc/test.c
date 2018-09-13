<?php

namespace App\Http\Controllers;

use App\Jobs\SendEmail;
use Illuminate\Support\Facades\Cache;
use Illuminate\Support\Facades\Log;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Mail;
use Illuminate\Support\Facades\Storage;

class StudentController extends Controller
{
    public function upload(Request $request)
    {

        if ($request->isMethod('POST')) {

//            var_dump($_FILES);
            $file = $request->file('source');   //从前端获取文件信息

            //判断文件是否上传成功
            if ($file->isValid()) {

                //获取原文件名
                $originalName = $file->getClientOriginalName();
                //获取文件扩展名
                $extension =  $file->getClientOriginalExtension();
                //获取文件类型
                $type = $file->getClientMimeType();
                //获取临时文件绝对路径
                $realPath = $file->getRealPath();

                //定义文件名
                $filename = date('Y-m-d-H-i-s', time()) . '-' . uniqid() . '-' . $extension;

                //规定保存到uploads磁盘,并用put()方法验证(第一个参数为文件名,第二个参数为文件)
                $bool = Storage::disk('uploads')->put($filename, file_get_contents($realPath));

//                var_dump($bool);

                if ($bool) {
                    return redirect('api/upload')->with('success', '上传成功');
                } else {
                    return redirect()->back()->with('error', '上传失败');
                }

            }

//            dd($file);
            exit;
        }

        return view('student.upload');
    }

    public function mail()
    {

//        Mail::raw('邮件内容', function ($message) {
//
//            $message->from('jat1014@163.com', 'xj');
//            $message->subject('邮件主题');
//            $message->to('240151541@qq.com');
//
//        });


        Mail::send('student.mail', ['name' => 'xj'], function ($message) {

            $message->to('240151541@qq.com');

        });    //第一个参数是视图模板的地址,第二个是参数,第三个是方法

    }

    public function cache1()    //添加缓存
    {
        // put()
        Cache::put('key1', 'value1', 10); //第一个参数为键值,第二个为值,第三个为缓存有效期(单位为分钟)

        // add() 缓存不存在时添加数据到缓存,成功添加返回true,失败返回false
        $bool = Cache::add('key2', 'value2', 10);
        dd($bool);

        // forever() 永久存储数据到缓存,必须通过forget()方法手动从缓存中移除
        Cache::forever('key3', 'value3');

        // 检查缓存项是否存在: has()
        if (Cache::has('key1')) {
            //
        }

    }

    public function cache2()    //获取及删除缓存
    {
        // get()
        $value = Cache::get('key3', 'default');
        dd($value);

        // pull() 获取并删除缓存,不存在则返回null
        $res = Cache::pull('key1', 'default');
        dd($res);

        // forget()
        $bool = Cache::forget('key3');
        dd($bool);

        // flush() 清除所有缓存
        Cache::flush();

    }

    public function error()
    {
//        dd($name);

        return view('student.error');
    }

    public function log()
    {
//        Log::info('这是一个info级别的日志');
//        Log::warning('这是一个warning级别的日志');

        Log::error('记录数组', ['name' => 'xj']);
    }

    public function queue()
    {
        //推送任务到队列(未执行,存在了对应的jobs数据表中)
        dispatch(new SendEmail('240151541@qq.com'));

    }

}
50/58/30