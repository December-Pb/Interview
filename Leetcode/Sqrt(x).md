#### Leetcode 69

Implement `int sqrt(int x)`.

Compute and return the square root of x, where x is guaranteed to be a non-negative integer.

Since the return type is an integer, the decimal digits are truncated and only the integer part of the result is returned.

Example 1:

```md
Input: 4
Output: 2
```

Example 2:

```md
Input: 8
Output: 2
Explanation: The square root of 8 is 2.82842..., and since the decimal part is truncated, 2 is returned.
```

Solution 1:
可以利用$\sqrt{x} = e^{\frac{1}{2}\log{x}}$求解:

```java
    public int sqrt(x) {
        if(x < 2) {
            return x;
        }
        int left = (int)Math.pow(Math.E, 0.5 * Math.log(x));
        int right = left + 1;
        return (long)right * right > x ? left : right;
    }
```

Solution 2:
Binary Search:
此题相当于从一个已排序数组中找到一个元素$x$使得$x^{2}$小于等于target

```java
    public int sqrt(x) {
        if(x < 2) {
            return x;
        }
        int left = 2;
        int right = x / 2;//平方根都小于target的一半
        while(left <= right) {
            int mid = left + (right - left) / 2;
            long number = mid * mid;//防止溢出
            if(number == x) {
                return mid;
            }
            else if(number < x) {
                mid = left + 1;
            }
            else if(number > x) {
                mid = right - 1;
            }
        }
        return right;//此处由于是要得到一个小于等于target的元素，所以最后一步更新right的时候会得到正确结果，而如果要得到一个大于等于target的元素，最后一步更新left会得到正确的结果(此时应该返回left)
    }
```

Solution 3:
牛顿迭代法:
想要求得一个数$a$的平方根，相当于求解$x$使得$x^{2} = a$,相当于求解$f(x) = x^{2} - a$的零点，利用牛顿迭代法可以得知:在$x_{n}$处的切线表达式为:$$y=f'(x_{n})(x-x_{n})+f(x_{n})$$
此切线与x轴的交点为$x_{n+1}$则:
$$0=f'(x_{n})(x_{n+1}-x_{n})+f(x_{n})$$ 
$$x_{n+1} = x_{n} - \frac{f(x_{n})}{f'(x_{n})}$$
在此题中:
$$x_{n+1} = x_{n} - \frac{x_{n}^{2}-a}{2x_{n}} = \frac{1}{2}(x_{n} + \frac{a}{x_{n}})$$
根据次式可以迭代更新$x_{n+1}$,当$|x_{n+1}-x_{n}|\leq1$时，停止迭代。

```java
    public int sqrt(int x) {
        if(x < 2) {
            return x;
        }
        double x0 = x;
        double x1 = (x + x / x0) / 2; 
        //此处虽然x == x0但是不能+1
        //因为x是int类型，如果x=2147483647的时候x+1会溢出
        while(Math.abs(x0 - x1) >= 1) {
            x0 = x1;
            x1 = (x0 + x / x0) / 2.0;
        }
        return (int)x1;
    }
```
