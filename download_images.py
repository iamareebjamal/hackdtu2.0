import os
import requests
import sys


urls = {
    'ringworm': 'http://www.dermnet.com/dn2/allJPG3/tinea-face',
    'vitiligo': 'http://www.dermnet.com/dn2/allJPG3/vitiligo-1.jpg',
}


def main(n):
    for disease in urls.keys():
        if not os.path.exists(disease):
            os.makedirs(disease)
        for count in range(1, n+1):
            prepend = '-%d.jpg' % (count)
            q = requests.get(urls[disease] + prepend)

            filename = os.path.join(disease, str(count) + ".jpg")
            with open(filename, "w") as f:
                f.write(q.content)


if __name__ == '__main__':
    if len(sys.argv) != 2:
        print("Enter number of images to be downloaded.")
        sys.exit()

    n = int(sys.argv[1])
    main(n)
