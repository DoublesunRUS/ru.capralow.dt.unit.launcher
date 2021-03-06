/**
 * Copyright (c) 2020, Alexander Kapralov
 */
package ru.capralow.dt.internal.launching.ui.launchconfigurations;

import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com._1c.g5.v8.dt.core.platform.IV8ProjectManager;
import com._1c.g5.v8.dt.internal.launching.ui.LaunchingUiPlugin;
import com._1c.g5.v8.dt.internal.launching.ui.launchconfigurations.AbstractRuntimeClientTab;
import com._1c.g5.v8.dt.platform.services.ui.AutoCompleteComboViewer;
import com.google.common.base.Strings;
import com.google.inject.Inject;

import ru.capralow.dt.unit.launcher.UnitTestLaunchConfigurationAttributes;
import ru.capralow.dt.unit.launcher.frameworks.FrameworkUtils;
import ru.capralow.dt.unit.launcher.frameworks.gson.FrameworkSettings;

public class UnitTestLaunchTab
    extends AbstractRuntimeClientTab
    implements SelectionListener, ISelectionChangedListener
{

    @Inject
    private IV8ProjectManager projectManager;

    private ComboViewer extensionProjectViewer;
    private ComboViewer extensionModuleViewer;
    private ComboViewer extensionTagViewer;

    private Button runExtensionTests;
    private Button runModuleTests;
    private Button runTagTests;

    public UnitTestLaunchTab()
    {
        setMessage(Messages.UnitTestLaunchTab_Tab_message);
    }

    @Override
    public void createControl(Composite parent)
    {
        Composite composite = new Group(parent, SWT.NONE);
        setControl(composite);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(), getHelpContextId());
        GridLayoutFactory.swtDefaults().applyTo(composite);
        composite.setFont(parent.getFont());

        createFeaturesSettings(composite);

        updateLaunchConfigurationDialog();
    }

    @Override
    public void doInitializeFrom(ILaunchConfiguration configuration)
    {
        try
        {
            String extensionProjectName = configuration
                .getAttribute(UnitTestLaunchConfigurationAttributes.EXTENSION_PROJECT_TO_TEST, (String)null);

            Boolean runExtensionTestsValue =
                configuration.getAttribute(UnitTestLaunchConfigurationAttributes.RUN_EXTENSION_TESTS, true);
            Boolean runModuleTestsValue =
                configuration.getAttribute(UnitTestLaunchConfigurationAttributes.RUN_MODULE_TESTS, false);
            Boolean runTagTestsValue =
                configuration.getAttribute(UnitTestLaunchConfigurationAttributes.RUN_TAG_TESTS, false);

            String extensionModuleName = configuration
                .getAttribute(UnitTestLaunchConfigurationAttributes.EXTENSION_MODULE_TO_TEST, (String)null);

            String extensionTagName =
                configuration.getAttribute(UnitTestLaunchConfigurationAttributes.EXTENSION_TAG_TO_TEST, (String)null);

            Collection<IProject> projects = FrameworkUtils.getExtensionProjects(projectManager);
            if (projects != null)
                extensionProjectViewer.setInput(projects);

            IProject project = FrameworkUtils.getConfigurationProject(extensionProjectName, projectManager);
            extensionProjectViewer
                .setSelection(project == null ? StructuredSelection.EMPTY : new StructuredSelection(project));

            runExtensionTests.setSelection(runExtensionTestsValue);
            runModuleTests.setSelection(runModuleTestsValue);
            runTagTests.setSelection(runTagTestsValue);

            extensionModuleViewer.getControl().setEnabled(runModuleTestsValue);
            extensionTagViewer.getControl().setEnabled(runTagTestsValue);

            String module = FrameworkUtils.getModuleByName(extensionModuleName, project, projectManager);
            extensionModuleViewer
                .setSelection(module == null ? StructuredSelection.EMPTY : new StructuredSelection(module));

            String tag = FrameworkUtils.getTagByName(extensionTagName, project, projectManager);
            extensionTagViewer
                .setSelection(extensionTagName == null ? StructuredSelection.EMPTY : new StructuredSelection(tag));

        }
        catch (CoreException e)
        {
            LaunchingUiPlugin.log(e);

        }

    }

    @Override
    public Image getImage()
    {
        String imagePath = "ru.capralow.dt.launching.ui/obj16/1Unit.png"; //$NON-NLS-1$
        Image image = LaunchingUiPlugin.getImage(imagePath);
        if (image == null)
        {
            ImageRegistry registry = LaunchingUiPlugin.getDefault().getImageRegistry();
            registry.put(imagePath, AbstractUIPlugin.imageDescriptorFromPlugin("ru.capralow.dt.launching.ui", //$NON-NLS-1$
                "icons/obj16/1Unit.png")); //$NON-NLS-1$
            image = registry.get(imagePath);
        }
        return image;
    }

    @Override
    public String getName()
    {
        return "1Unit"; //$NON-NLS-1$
    }

    @Override
    public boolean isValid(ILaunchConfiguration configuration)
    {
        try
        {
            Boolean isExtensionValid = getSelectedExtensionProject() != null;

            Boolean isModuleValid = getSelectedModule() != null || !runModuleTests.getSelection();

            Boolean isTagValid = getSelectedTag() != null || !runTagTests.getSelection();

            String externalObjectDumpPath = configuration
                .getAttribute(UnitTestLaunchConfigurationAttributes.EXTERNAL_OBJECT_DUMP_PATH, (String)null);
            String externalObjectStartupOptions = configuration
                .getAttribute(UnitTestLaunchConfigurationAttributes.EXTERNAL_OBJECT_STARTUP_OPTIONS, (String)null);
            Boolean isExternalObjectValid =
                !Strings.isNullOrEmpty(externalObjectDumpPath) && !Strings.isNullOrEmpty(externalObjectStartupOptions);

            return isExtensionValid && isModuleValid && isTagValid && isExternalObjectValid
                && super.isValid(configuration);

        }
        catch (CoreException e)
        {
            LaunchingUiPlugin.log(e);

        }

        return false;
    }

    @Override
    public void performApply(ILaunchConfigurationWorkingCopy configuration)
    {
        Boolean runModuleTestsValue = runModuleTests.getSelection();
        Boolean runTagTestsValue = runTagTests.getSelection();

        configuration.setAttribute(UnitTestLaunchConfigurationAttributes.RUN_EXTENSION_TESTS,
            runExtensionTests.getSelection());
        configuration.setAttribute(UnitTestLaunchConfigurationAttributes.RUN_MODULE_TESTS, runModuleTestsValue);
        configuration.setAttribute(UnitTestLaunchConfigurationAttributes.RUN_TAG_TESTS, runTagTestsValue);

        IProject project = getSelectedExtensionProject();
        configuration.setAttribute(UnitTestLaunchConfigurationAttributes.EXTENSION_PROJECT_TO_TEST,
            project == null ? null : project.getName());

        String commonModule = getSelectedModule();
        configuration.setAttribute(UnitTestLaunchConfigurationAttributes.EXTENSION_MODULE_TO_TEST,
            !runModuleTestsValue || commonModule == null ? null : commonModule);

        String tag = getSelectedTag();
        configuration.setAttribute(UnitTestLaunchConfigurationAttributes.EXTENSION_TAG_TO_TEST,
            !runTagTestsValue || tag == null ? null : tag);

        String paramsFilePathName = FrameworkUtils.getConfigurationFilesPath(configuration);

        FrameworkSettings frameworkSettings = FrameworkUtils.getFrameworkSettings();
        String startupOption = FrameworkUtils.getFrameworkStartupOptions(frameworkSettings, paramsFilePathName);

        configuration.setAttribute(UnitTestLaunchConfigurationAttributes.EXTERNAL_OBJECT_STARTUP_OPTIONS,
            startupOption);
        configuration.setAttribute(UnitTestLaunchConfigurationAttributes.EXTERNAL_OBJECT_DUMP_PATH,
            paramsFilePathName + "framework.epf"); //$NON-NLS-1$
    }

    @Override
    public void selectionChanged(SelectionChangedEvent event)
    {
        updateLaunchConfigurationDialog();
    }

    @Override
    public void setDefaults(ILaunchConfigurationWorkingCopy configuration)
    {
        configuration.setAttribute(UnitTestLaunchConfigurationAttributes.RUN_EXTENSION_TESTS, true);
        configuration.setAttribute(UnitTestLaunchConfigurationAttributes.RUN_MODULE_TESTS, false);
        configuration.setAttribute(UnitTestLaunchConfigurationAttributes.RUN_TAG_TESTS, false);
    }

    @Override
    public void widgetDefaultSelected(SelectionEvent event)
    {
        updateLaunchConfigurationDialog();
    }

    @Override
    public void widgetSelected(SelectionEvent event)
    {
        updateLaunchConfigurationDialog();
    }

    private void createFeaturesSettings(Composite parent)
    {
        Composite composite = new Composite(parent, SWT.NONE);
        GridDataFactory.fillDefaults().grab(true, false).applyTo(composite);
        GridLayoutFactory.fillDefaults().numColumns(2).applyTo(composite);

        // 1.1
        Label launchProjectAll = new Label(composite, SWT.NONE);
        launchProjectAll.setText(Messages.UnitTestLaunchTab_ExtensionProject_to_Test);
        // 1.2
        extensionProjectViewer = new AutoCompleteComboViewer(composite);
        GridDataFactory.fillDefaults()
            .align(4, 16777216)
            .grab(true, false)
            .hint(200, -1)
            .applyTo(extensionProjectViewer.getControl());
        extensionProjectViewer.setContentProvider(ArrayContentProvider.getInstance());
        extensionProjectViewer.setLabelProvider(new WorkbenchLabelProvider());
        extensionProjectViewer.setComparator(new ViewerComparator());
        ISelectionChangedListener projectViewerListener = event -> {
            StructuredSelection selection = (StructuredSelection)event.getSelection();
            if (!selection.isEmpty())
            {
                IProject project = (IProject)selection.getFirstElement();

                List<String> modules = FrameworkUtils.getTestModules(project.getLocation());
                if (modules != null)
                    extensionModuleViewer.setInput(modules);

                List<String> tags = FrameworkUtils.getTestTags(project.getLocation());
                if (tags != null)
                    extensionTagViewer.setInput(tags);
            }

            updateLaunchConfigurationDialog();
        };
        extensionProjectViewer.addSelectionChangedListener(projectViewerListener);

        // 2.1
        runExtensionTests = createRadioButton(composite, Messages.UnitTestLaunchTab_RunExtensionTests);
        SelectionListener runExtensionTestsListener = SelectionListener.widgetSelectedAdapter(event -> {
            Button element = (Button)event.getSource();
            if (element.getSelection())
            {
                extensionModuleViewer.getControl().setEnabled(false);
                extensionTagViewer.getControl().setEnabled(false);
            }

            updateLaunchConfigurationDialog();
        });
        runExtensionTests.addSelectionListener(runExtensionTestsListener);
        // 2.2
        new Label(composite, SWT.NONE);

        // 3.1
        runModuleTests = createRadioButton(composite, Messages.UnitTestLaunchTab_RunModuleTests);
        SelectionListener runModuleTestsListener = SelectionListener.widgetSelectedAdapter(event -> {
            Button element = (Button)event.getSource();
            if (element.getSelection())
            {
                extensionModuleViewer.getControl().setEnabled(true);
                extensionTagViewer.getControl().setEnabled(false);
            }

            updateLaunchConfigurationDialog();
        });
        runModuleTests.addSelectionListener(runModuleTestsListener);
        // 3.2
        new Label(composite, SWT.NONE);

        // 4.1
        Label launchModule = new Label(composite, SWT.NONE);
        launchModule.setText(Messages.UnitTestLaunchTab_ExtensionModule_to_Test);
        // 4.2
        extensionModuleViewer = new AutoCompleteComboViewer(composite);
        GridDataFactory.fillDefaults()
            .align(4, 16777216)
            .grab(true, false)
            .hint(200, -1)
            .applyTo(extensionModuleViewer.getControl());
        extensionModuleViewer.setContentProvider(ArrayContentProvider.getInstance());
        extensionModuleViewer.setLabelProvider(new LabelProvider());
        extensionModuleViewer.setComparator(new ViewerComparator());
        extensionModuleViewer.addSelectionChangedListener(this);

        // 5.1
        runTagTests = createRadioButton(composite, Messages.UnitTestLaunchTab_RunTagTests);
        SelectionListener runTagTestsListener = SelectionListener.widgetSelectedAdapter(event -> {
            Button element = (Button)event.getSource();
            if (element.getSelection())
            {
                extensionModuleViewer.getControl().setEnabled(false);
                extensionTagViewer.getControl().setEnabled(true);
            }

            updateLaunchConfigurationDialog();
        });
        runTagTests.addSelectionListener(runTagTestsListener);
        // 5.2
        new Label(composite, SWT.NONE);

        // 6.1
        Label launchTag = new Label(composite, SWT.NONE);
        launchTag.setText(Messages.UnitTestLaunchTab_ExtensionTag_to_Test);
        // 6.2
        extensionTagViewer = new AutoCompleteComboViewer(composite);
        GridDataFactory.fillDefaults()
            .align(4, 16777216)
            .grab(true, false)
            .hint(200, -1)
            .applyTo(extensionTagViewer.getControl());
        extensionTagViewer.setContentProvider(ArrayContentProvider.getInstance());
        extensionTagViewer.setLabelProvider(new LabelProvider());
        extensionTagViewer.setComparator(new ViewerComparator());
        extensionTagViewer.addSelectionChangedListener(this);
    }

    private IProject getSelectedExtensionProject()
    {
        IStructuredSelection selection = extensionProjectViewer.getStructuredSelection();
        return !selection.isEmpty() && selection.getFirstElement() instanceof IProject
            ? (IProject)selection.getFirstElement() : null;
    }

    private String getSelectedModule()
    {
        IStructuredSelection selection = extensionModuleViewer.getStructuredSelection();
        return !selection.isEmpty() && selection.getFirstElement() instanceof String
            ? (String)selection.getFirstElement() : null;
    }

    private String getSelectedTag()
    {
        IStructuredSelection selection = extensionTagViewer.getStructuredSelection();
        return !selection.isEmpty() && selection.getFirstElement() instanceof String
            ? (String)selection.getFirstElement() : null;
    }

}
