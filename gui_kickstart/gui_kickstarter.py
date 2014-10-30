#!/bin/python
import os
import shutil


def main():
    class_name = raw_input('Enter new Class Name(e.g. ApptList): ');
    parent_name = raw_input('Enter the JComponent to extends(e.g. JFrame): ');

    print 'Generating...'

    gui_dir = 'generated_tree/hkust/cse/calendar/gui/'
    controller_dir = gui_dir + 'controller/'
    view_dir = gui_dir + 'view/'
    view_base_dir = view_dir + 'base/'

    try:
        shutil.rmtree('generated_tree');
    except Exception:
        pass
    os.makedirs(view_dir)
    os.makedirs(view_base_dir)
    os.makedirs(controller_dir)


    template_file = open('ClassController.java', 'r')
    to_file = open("%s%sController.java" % (controller_dir, class_name), 'w+')
    generate_file(template_file, to_file, class_name, parent_name)


    template_file = open('ClassControllerEvent.java', 'r')
    to_file = open("%s%sControllerEvent.java" % (controller_dir, class_name), 'w+')
    generate_file(template_file, to_file, class_name, parent_name)


    template_file = open('PrimClassView.java', 'r')
    to_file = open("%sPrim%sView.java" % (view_dir, class_name), 'w+')
    generate_file(template_file, to_file, class_name, parent_name)


    template_file = open('BaseClassView.java', 'r')
    to_file = open("%sBase%sView.java" % (view_base_dir, class_name), 'w+')
    generate_file(template_file, to_file, class_name, parent_name)

    print "Done!"


def generate_file(template, to, class_name, parent_name):
    for line in template:
        line = line.replace('{placeholder}', class_name)
        line = line.replace('{placeholder_ex}', parent_name)
        to.write(line)


if __name__ == '__main__':
    main()
