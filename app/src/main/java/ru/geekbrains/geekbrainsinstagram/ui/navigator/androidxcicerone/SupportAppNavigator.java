package ru.geekbrains.geekbrainsinstagram.ui.navigator.androidxcicerone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.LinkedList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.commands.Back;
import ru.terrakok.cicerone.commands.BackTo;
import ru.terrakok.cicerone.commands.Command;
import ru.terrakok.cicerone.commands.Forward;
import ru.terrakok.cicerone.commands.Replace;

public final class SupportAppNavigator implements Navigator {

    private final Activity activity;
    private final FragmentManager fragmentManager;
    private final int containerId;
    private LinkedList<String> localStackCopy;

    public SupportAppNavigator(FragmentActivity activity, int containerId) {
        this(activity, activity.getSupportFragmentManager(), containerId);
    }

    private SupportAppNavigator(FragmentActivity activity, FragmentManager fragmentManager, int containerId) {
        this.activity = activity;
        this.fragmentManager = fragmentManager;
        this.containerId = containerId;
    }

    @Override
    public void applyCommands(Command[] commands) {
        fragmentManager.executePendingTransactions();

        copyStackToLocal();

        for (Command command : commands) {
            applyCommand(command);
        }
    }

    private void copyStackToLocal() {
        localStackCopy = new LinkedList<>();

        final int stackSize = fragmentManager.getBackStackEntryCount();
        for (int i = 0; i < stackSize; i++) {
            localStackCopy.add(fragmentManager.getBackStackEntryAt(i).getName());
        }
    }

    private void applyCommand(Command command) {
        if (command instanceof Forward) {
            activityForward((Forward) command);
        } else if (command instanceof Replace) {
            activityReplace((Replace) command);
        } else if (command instanceof BackTo) {
            backTo((BackTo) command);
        } else if (command instanceof Back) {
            fragmentBack();
        }
    }


    private void activityForward(Forward command) {
        SupportAppScreen screen = (SupportAppScreen) command.getScreen();
        Intent activityIntent = screen.getActivityIntent(activity);

        if (activityIntent != null) {
            Bundle options = createStartActivityOptions(command, activityIntent);
            checkAndStartActivity(screen, activityIntent, options);
        } else {
            fragmentForward(command);
        }
    }

    private void fragmentForward(Forward command) {
        SupportAppScreen screen = (SupportAppScreen) command.getScreen();
        Fragment fragment = createFragment(screen);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        setupFragmentTransaction(
                command,
                fragmentManager.findFragmentById(containerId),
                fragment,
                fragmentTransaction
        );

        fragmentTransaction
                .replace(containerId, fragment)
                .addToBackStack(screen.getScreenKey())
                .commit();
        localStackCopy.add(screen.getScreenKey());
    }

    private void fragmentBack() {
        if (localStackCopy.size() > 0) {
            fragmentManager.popBackStack();
            localStackCopy.removeLast();
        } else {
            activityBack();
        }
    }

    private void activityBack() {
        activity.finish();
    }

    private void activityReplace(Replace command) {
        SupportAppScreen screen = (SupportAppScreen) command.getScreen();
        Intent activityIntent = screen.getActivityIntent(activity);

        if (activityIntent != null) {
            Bundle options = createStartActivityOptions(command, activityIntent);
            checkAndStartActivity(screen, activityIntent, options);
            activity.finish();
        } else {
            fragmentReplace(command);
        }
    }

    private void fragmentReplace(Replace command) {
        SupportAppScreen screen = (SupportAppScreen) command.getScreen();
        Fragment fragment = createFragment(screen);

        if (localStackCopy.size() > 0) {
            fragmentManager.popBackStack();
            localStackCopy.removeLast();

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            setupFragmentTransaction(
                    command,
                    fragmentManager.findFragmentById(containerId),
                    fragment,
                    fragmentTransaction
            );

            fragmentTransaction
                    .replace(containerId, fragment)
                    .addToBackStack(screen.getScreenKey())
                    .commit();
            localStackCopy.add(screen.getScreenKey());

        } else {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            setupFragmentTransaction(
                    command,
                    fragmentManager.findFragmentById(containerId),
                    fragment,
                    fragmentTransaction
            );

            fragmentTransaction
                    .replace(containerId, fragment)
                    .commit();
        }
    }

    private void backTo(BackTo command) {
        if (command.getScreen() == null) {
            backToRoot();
        } else {
            String key = command.getScreen().getScreenKey();
            int index = localStackCopy.indexOf(key);
            int size = localStackCopy.size();

            if (index != -1) {
                for (int i = 1; i < size - index; i++) {
                    localStackCopy.removeLast();
                }
                fragmentManager.popBackStack(key, 0);
            } else {
                backToUnexisting((SupportAppScreen) command.getScreen());
            }
        }
    }

    private void backToRoot() {
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        localStackCopy.clear();
    }

    /**
     * Override this method to setup fragment transaction {@link FragmentTransaction}.
     * For example: setCustomAnimations(...), addSharedElement(...) or setReorderingAllowed(...)
     *
     * @param command             current navigation command. Will be only {@link Forward} or {@link Replace}
     * @param currentFragment     current fragment in container
     *                            (for {@link Replace} command it will be screen previous in new chain, NOT replaced screen)
     * @param nextFragment        next screen fragment
     * @param fragmentTransaction fragment transaction
     */
    private void setupFragmentTransaction(Command command,
                                          Fragment currentFragment,
                                          Fragment nextFragment,
                                          FragmentTransaction fragmentTransaction) {
    }

    /**
     * Override this method to create option for start activity
     *
     * @param command        current navigation command. Will be only {@link Forward} or {@link Replace}
     * @param activityIntent activity intent
     * @return transition options
     */
    private Bundle createStartActivityOptions(Command command, Intent activityIntent) {
        return null;
    }

    private void checkAndStartActivity(SupportAppScreen screen, Intent activityIntent, Bundle options) {
        // Check if we can start activity
        if (activityIntent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivity(activityIntent, options);
        } else {
            unexistingActivity(screen, activityIntent);
        }
    }

    /**
     * Called when there is no activity to open {@code screenKey}.
     *
     * @param screen         screen
     * @param activityIntent intent passed to start Activity for the {@code screenKey}
     */
    private void unexistingActivity(SupportAppScreen screen, Intent activityIntent) {
        // Do nothing by default
    }

    /**
     * Creates Fragment matching {@code screenKey}.
     *
     * @param screen screen
     * @return instantiated fragment for the passed screen
     */
    private Fragment createFragment(SupportAppScreen screen) {
        Fragment fragment = screen.getFragment();

        if (fragment == null) {
            errorWhileCreatingScreen(screen);
        }
        return fragment;
    }

    /**
     * Called when we tried to fragmentBack to some specific screen (via {@link BackTo} command),
     * but didn't found it.
     *
     * @param screen screen
     */
    private void backToUnexisting(SupportAppScreen screen) {
        backToRoot();
    }

    private void errorWhileCreatingScreen(SupportAppScreen screen) {
        throw new RuntimeException("Can't create a screen: " + screen.getScreenKey());
    }
}