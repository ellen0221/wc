<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Order;


class OrderController extends Controller
{
    // 接受用户订单信息并写入队列
    public function order(Request $request)
    {
        $info = $request->input();
        $moile = $info['mobile'];

        if ($request->isMethod('POST')) {

            if (!empty($info['mobile'])) {
                //订单中心处理流程
                // ...

                $order_id = rand(10000,99999);

                //生成的订单信息存入队列表中

                $user = Order::where('mobile', '=', $moile)->update([
                    'order_id' => $order_id,
                    'status' => 0,
                    'created_at' => $this->now()
                ]);

                if ($user) {
                    return $order_id . '保存成功';
                }
                return '保存失败';

            }

        }

    }

    public function create()
    {
        $user = Order::create([
            'order_id' => 0,
            'mobile' => '19234750228',
            'status' => 0,
            'created_at' => date('Y-m-d H:i:s', time())
        ]);
        if (!empty($user)) {
            return 'success';
        }
        return 'error';

    }

    public function now()
    {
        return date('Y-m-d H:i:s', time());
    }
}
32/28/4